package com.hari.tweetmanager.service.impl;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.hari.tweetmanager.Exception.TweetManagerException;
import com.hari.tweetmanager.dto.Tweet;
import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.TweetDao;
import com.hari.tweetmanager.mapper.TweetMapper;
import com.hari.tweetmanager.service.UrlDao;
import com.hari.tweetmanager.service.UserDao;
import com.hari.tweetmanager.utils.Constants;
import com.hari.tweetmanager.utils.DateTimeUtils;
import com.hari.tweetmanager.utils.HttpUtils;
import com.hari.tweetmanager.utils.MySqlQueries;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import net.spy.memcached.MemcachedClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.UriBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.sql.*;
import java.util.*;

@Service
public class TweetDaoImpl implements TweetDao {

    @Autowired
    MemcachedClient memcachedClient;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AuthDao authDao;

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    UserDao userDao;

    @Autowired
    UrlDao urlDao;

    static Logger logger = Logger.getLogger(TweetDaoImpl.class);

    /* Use max_id and since_id  for fetching tweet data
       *max_id* : an application’s first request to a timeline endpoint should only specify a count.
                When processing this and subsequent responses, keep track of the lowest ID received.
                This ID should be passed as the value of the max_id parameter for the next request, which will only return Tweets with IDs lower than or equal to the value of the max_id parameter.
       *since_id* : greatest ID of all the Tweets your application has already processed
       Applications which use both the max_id and since_id parameters correctly minimize the amount of redundant data they fetch and process,
       while retaining the ability to iterate over the entire available contents of a timeline.
        */
    @Override
    public List<Tweet> getTweets() {

        /*Steps to use max_id and since_id :
        * Make a first request just by using count - Keep track of the lowest id of the tweets received
        * Make another requests by passing max_id = (lowestId - 1) you gathered above - again keep track of lowest id of tweets received
        * Make further requests by passing max_id as (lowestTweetId - 1) received in previous requests - Keep doing it.
        *
        * since_id :
        * You process the tweets in batches : After processing a batch, keep track of highest tweetId received :
        * When processing next batch use the highestTweetId that you got above and use it as since_id for every request in next batch
        * Repeat the same by passing since_id as the highestTweetId that is received in previous batch
        *
        * More Info : https://developer.twitter.com/en/docs/tweets/timelines/guides/working-with-timelines
        * */

        /* Get the tweets from database - if there aren't any -> get 5 tweets and store it in database
           If you find some tweets, get the max tweetId -> use it as since_id for next request to get tweets
           THERE WON'T BE ANY SLEEP LOGIC -> EACH TIME YOU RUN THE APPLICATION, IT GETS TWEETS AND STORES IT IN DB
           each time you run get 5 tweets at a time and use max_id logic to avoid getting duplicate tweets
            */

        String requestBaseUrl = "https://api.twitter.com/1.1/statuses/home_timeline.json";
        String httpMethod = "GET";

        HashMap queryParams = new HashMap();
        List<Tweet> tweets = new ArrayList<Tweet>();
        boolean allGood = true;

        // This counter helps when trying out new features as we don't want to make more than 15 calls in each interval
        int loopCount = 0;

        // Query memcache to check if we can make another request
        try {
            Long timeTillNextRequest = (Long) memcachedClient.get(Constants.TIME_TILL_NEXT_REQUEST_KEY);
            Long currentTimeInEpoch = DateTimeUtils.getCurrentTimeInSecondsInEpoch();

            if (timeTillNextRequest != null && currentTimeInEpoch < timeTillNextRequest) {
                logger.info("You can only make 15 requests in 15 minutes interval to timeline API. Don't make any requests for next : "
                        + (timeTillNextRequest - currentTimeInEpoch) + " seconds");
                return tweets;
            }
        }
        catch (Exception e) {
            logger.error("Error when connecting to memcache" , e);
        }

        do {
            try {
                // When making first call - check if DB contains any tweets at all
                Long largestTweetId = getLargestTweetIdInDb();

                // largestTweetId = null in very first request when DB has no records
                if (largestTweetId != null) {
                    queryParams.put("since_id", largestTweetId.toString());
                }

                // number of tweets we want to retrieve in timeline in one call
                queryParams.put("count", "10");

                String authHeader = authDao.getAuthHeader(httpMethod, requestBaseUrl, queryParams);

                ClientResponse tweetResponse = HttpUtils.sendGetRequest(requestBaseUrl, queryParams, authHeader);

                Long requestsLeft = Long.parseLong(tweetResponse.getHeaders().get("x-rate-limit-remaining").get(0));

                // Standard API's can only make 15 requests in 15 minutes time window
                if(requestsLeft == 0) {
                    Long timeTillNextRequestInEpoch = Long.parseLong(tweetResponse.getHeaders().get("x-rate-limit-reset").get(0));

                    logger.info("Do not send any more requests to twitter API for " +
                                (timeTillNextRequestInEpoch - DateTimeUtils.getCurrentTimeInSecondsInEpoch()) + " seconds. 15 requests per 15 minutes cap reached");
                    memcachedClient.set(Constants.TIME_TILL_NEXT_REQUEST_KEY, Constants.MEMCACHE_TTL, timeTillNextRequestInEpoch);
                    break;
                }

                String tweetData = tweetResponse.getEntity(String.class);

                JSONArray tweetsFromCurrentRequest = new JSONArray(tweetData);

                logger.debug("Tweets Retrieved in the request : " + tweetsFromCurrentRequest.length());

                // We don't have any more tweets to process
                if (tweetsFromCurrentRequest.length() == 0) {
                    allGood = false;
                } else {
                    logger.debug("Mapping tweet data into Pojos, processing one at a time");
                    // Convert JSONObjects to TweetPojo
                    for (int i = 0; i < tweetsFromCurrentRequest.length(); i++) {

                        JSONObject tweetsJSONObject = tweetsFromCurrentRequest.getJSONObject(i);
                        logger.trace("Id of the tweet : " + tweetsJSONObject.getLong("id"));

                        Tweet tweet = tweetMapper.convertToTweetObject(tweetsJSONObject);

                        tweets.add(tweet);
                    }

                    //Find lowest_id received in the current batch - max_id in next request = lowest_id in previous batch - 1
                    long lowestTweetIdInBatch = tweets.
                            stream().
                            mapToLong(tweet -> tweet.getTweetId()).
                            min().getAsLong();

                    logger.trace("Smallest tweetId in the batch : " + lowestTweetIdInBatch);

                    Long maxId = lowestTweetIdInBatch - 1;

                    queryParams.put("max_id", maxId.toString());
                }
            }
            catch (JSONException jse) {
                logger.error("JSON Parse Exception : ", jse);
            }
            catch (UnsupportedEncodingException use) {
                logger.error("Error while encoding data : ", use);
            }
            catch (TweetManagerException tme) {
                logger.error(tme.getMessage());
                break;
            }

        } while (allGood && loopCount++ != 4); // we want to loop 5 times in total

        logger.debug("Total number of tweets retrieved in current batch : " + tweets.size());

        return tweets;
    }

    @Override
    public void storeTweetsInDatabase(List<Tweet> tweets) {

        logger.debug("Storing : " + tweets.size() + " tweets returned in a batch in the database");

        for(int i=0 ; i < tweets.size(); i++) {
            try {

                Tweet tweet = tweets.get(i);

                // Create user first - pass that Id to save URL's and also to save tweets
                long userId = userDao.storeUser(tweet.getUser());

                long tweetId = storeTweet(tweet, userId);

                urlDao.storeUrls(tweet.getUrls(), null, tweetId);
            }
            catch (TweetManagerException tme) {
                logger.error(tme);
            }
        }
    }

    @Override
    public Long getLargestTweetIdInDb() {
        Long largestTweetId = null;

        try {
            largestTweetId = jdbcTemplate.queryForObject(MySqlQueries.SQL_LARGEST_TWEET_ID, Long.class);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Did not find any tweet in DB ", e);
        }

        return largestTweetId;
    }

    public long storeTweet(Tweet tweet, Long userId) throws TweetManagerException {
        // Save userId in tweets table

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(MySqlQueries.SQL_TWEET_CREATE,
                                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, tweet.getTweetCreatedAt());
                    ps.setLong(2, tweet.getTweetId());
                    ps.setLong(3, userId);
                    ps.setString(4, tweet.getText());
                    ps.setString(5, tweet.getSource());
                    ps.setString(6, tweet.getLanguage());
                    ps.setLong(7, tweet.getRetweetCount());
                    ps.setLong(8, tweet.getFavoriteCount());
                    ps.setBoolean(9, tweet.getFavorited());
                    ps.setBoolean(10, tweet.getRetweeted());
                    ps.setBoolean(11, tweet.getTruncated());
                    return ps;
                }
            }, keyHolder);
        } catch (Exception te) {
            throw new TweetManagerException("Error while storing tweet data ", te);
        }

        long tweetId = keyHolder.getKey().longValue();

        return tweetId;
    }

    @Override
    public void getTweets(int count) {

        String httpMethod = "GET";
        String requestBaseUrl = "https://api.twitter.com/1.1/statuses/home_timeline.json";

        HashMap queryParams = new HashMap();
        queryParams.put("count", String.valueOf(count));
        String authHeader = authDao.getAuthHeader(httpMethod, requestBaseUrl, queryParams);

        Client client = Client.create();
        URI baseURI = UriBuilder.fromUri(requestBaseUrl).build();
        WebResource webResource = client.resource(baseURI);

        try {
            // URLEncoding is not necessary here as there are no special chars in key or value of query params
            String tweetData = webResource.queryParam("count",String.valueOf(count))
                    .header("Authorization", authHeader)
                    .header("User-Agent","OAuth gem v0.4.4")
                    .header("Host","api.twitter.com")
                    .get(String.class);
            logger.debug("Response from Twitter API : " + tweetData);

            JSONArray tweets = new JSONArray(tweetData);
            logger.info("Tweet Data returned : " + tweets.toString());

        }
        catch (JSONException jse) {
            logger.error("JSON Parse Exception : " , jse);
        }
    }
}
