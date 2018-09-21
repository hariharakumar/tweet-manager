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
import com.hari.tweetmanager.utils.HttpUtils;
import com.hari.tweetmanager.utils.MySqlQueries;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
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
import java.util.stream.Collectors;

@Service
public class TweetDaoImpl implements TweetDao {

    @Autowired
    AuthDao authDao;

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    UserDao userDao;

    @Autowired
    UrlDao urlDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    static Logger logger = Logger.getLogger(TweetDaoImpl.class);

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
            System.out.println("Response from Twitter API : " + tweetData);

            JSONArray tweets = new JSONArray(tweetData);
            logger.info("Tweet Data returned : " + tweets.toString());

        }
        catch (JSONException jse) {
            logger.error("JSON Parse Exception : " , jse);
        }
    }

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
        boolean exit = false;
        int loopCount = 0; // We don't want to loop forever

        try {
            do {
                // When making first call - check if DB contains any tweets at all
                Long largestTweetId = getLargestTweetIdInDb();

                // largestTweetId = null in first ever request
                if (largestTweetId != null) {
                    queryParams.put("since_id", String.valueOf(largestTweetId));
                }

                // number of tweets we want to retrieve in timeline in one call
                queryParams.put("count", String.valueOf("5"));

                String authHeader = authDao.getAuthHeader(httpMethod, requestBaseUrl, queryParams);
                String tweetData = HttpUtils.sendGetRequest(requestBaseUrl, queryParams, authHeader);

                JSONArray tweetsFromCurrentRequest = new JSONArray(tweetData);

                System.out.println("Tweets Retrieved in the request : " + tweetsFromCurrentRequest.length());

                // We don't have any more tweets to process
                if(tweetsFromCurrentRequest.length() == 0) {
                    exit = true;
                }
                else {
                    // Convert JSONObjects to TweetPojo
                    for (int i = 0; i < tweetsFromCurrentRequest.length(); i++) {

                        JSONObject tweetsJSONObject = tweetsFromCurrentRequest.getJSONObject(i);

                        Tweet tweet = tweetMapper.convertToTweetObject(tweetsJSONObject);

                        tweets.add(tweet);
                    }

                    //Find lowest_id received in the current batch - max_id in next request = lowest_id in previous batch - 1
                    long lowestTweetIdInBatch = tweets.
                            stream().
                            mapToLong(tweet -> tweet.getTweetId()).
                            min().getAsLong();
                    System.out.println("Least tweetId in the batch : " + lowestTweetIdInBatch);

                    Long maxId = lowestTweetIdInBatch - 1;

                    queryParams.put("max_id", maxId);
                }
            } while (exit || loopCount++ == 20);
        }
        catch (JSONException jse) {
            logger.error("JSON Parse Exception : ", jse);
        }
        catch (UnsupportedEncodingException use) {
            logger.error("Error while encoding data : ", use);
        }
        catch (TweetManagerException tme) {
            logger.error(tme.getMessage());
        }

        return tweets;
    }

    @Override
    public void storeTweetsInDatabase(List<Tweet> tweets) {

        for(int i=0 ; i < tweets.size(); i++) {

            Tweet tweet = tweets.get(i);

            // Create user first - pass that Id to save URL's and also to save tweets
            long userId = userDao.storeUser(tweet.getUser());

            long tweetId = storeTweet(tweet, userId);

            urlDao.storeUrls(tweet.getUrls(), null, tweetId);
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

    public long storeTweet(Tweet tweet, Long userId) {
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
            logger.error("Error while storing tweet data ", te);
        }

        long tweetId = keyHolder.getKey().longValue();

        return tweetId;
    }
}
