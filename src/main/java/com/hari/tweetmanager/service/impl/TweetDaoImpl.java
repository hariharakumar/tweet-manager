package com.hari.tweetmanager.service.impl;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.hari.tweetmanager.dto.Tweet;
import com.hari.tweetmanager.dto.Url;
import com.hari.tweetmanager.dto.User;
import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.TweetDao;
import com.hari.tweetmanager.service.TweetMapper;
import com.hari.tweetmanager.utils.HttpUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.UriBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

@Service
public class TweetDaoImpl implements TweetDao {

    @Autowired
    AuthDao authDao;

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    static Logger logger = Logger.getLogger(TweetDaoImpl.class);

    public final String SQL_USER_CREATE =
            "insert into `users`(userId,name,screenName,location, description, userCreatedAt, " +
                    "favoritesCount,statusesCount,followersCount,friendsCount,language,)" +
                    " values(?,?,?,?,?,?,?)";

    public final String SQL_URL_CREATE =
            "insert into `urls`(url,expandedUrl,displayUrl,userId, tweetId" +
                    " values(?,?,?,?,?)";

    public final String SQL_TWEET_CREATE =
            "insert into `tweets`(tweetCreatedAt,tweetId,userId,text, source, language, " +
                    "retweetCount,favoriteCount,favorited,retweeted,truncated,)" +
                    " values(?,?,?,?,?,?,?)";


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
    public JSONArray getTweets() {

        /*Steps to use max_id and since_id :
        * Make a first request just by using count - Keep track of the lowest id of the tweets received
        * Make another requests by passing max_id - this value will be (lowestId - 1) you gathered above - again keep track of lowest id of tweets received
        * Make further requests by passing max_id as (lowestTweetId - 1) received in previous requests - Keep doing it.
        *
        * since_id :
        * You process the tweets in batches : After processing a batch, keep track of highest tweetId received :
        * When processing next batch use the highestTweetId that you got above and use it as since_id for every request in next batch
        * Repeat the same by passing since_id as the highestTweetId that is received in previous batch
        *
        * More Info : https://developer.twitter.com/en/docs/tweets/timelines/guides/working-with-timelines
        * */

        /* Get the tweets from database - if there aren't any -> get 200 tweets and store it in database
           If you find some tweets, get the max tweetId -> use it as since_id for next request to get tweets
           THERE WON'T BE ANY SLEEP LOGIC -> EACH TIME YOU RUN THE APPLICATION, IT GETS TWEETS AND STORES IT IN DB
           each time you run get 5 tweets at a time and use max_id logic to avoid getting duplicate tweets
            */

        String httpMethod = "GET";
        String requestBaseUrl = "https://api.twitter.com/1.1/statuses/home_timeline.json";

        HashMap queryParams = new HashMap();
        queryParams.put("count", String.valueOf("10"));

        JSONArray tweets = new JSONArray();

        try {

            String authHeader = authDao.getAuthHeader(httpMethod, requestBaseUrl, queryParams);
            String tweetData = HttpUtils.sendGetRequest(requestBaseUrl, queryParams, authHeader);

            tweets = new JSONArray(tweetData);

            System.out.println("Tweets Count : " + tweets.length());
            return tweets;
        }
        catch (JSONException jse) {
            logger.error("JSON Parse Exception : ", jse);
        }
        catch (UnsupportedEncodingException use) {
            logger.error("Error while encoding data : ", use);
        }

        return tweets;
    }

    @Override
    public void storeTweetsInDatabase(JSONArray tweets) {

        for(int i=0 ; i< tweets.length(); i++) {

            try {
                JSONObject tweetsJSONObject = tweets.getJSONObject(i);

                Tweet tweet = tweetMapper.convertToTweetObject(tweetsJSONObject);

                long userId = storeUser(tweet.getUser());

                long tweetId = storeTweet(tweet, userId);

                storeUrls(tweet.getUrls(), null, tweetId);

            }
            catch (JSONException jse) {
                logger.error("Error while parsing tweet json object" , jse);
            }
        }
    }

    // Create user first - pass that Id to save URL's and also to save tweets
    public long storeUser(User user) {

        // After storing user in DB - call storeURL to store the URL associated to the user - get Id and store it in user table

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_USER_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, user.getUserId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getScreenName());
                ps.setString(4, user.getLocation());
                ps.setString(5, user.getDescription());
                ps.setString(6, user.getUserCreatedAt());
                ps.setLong(7, user.getFavoritesCount());
                ps.setLong(8, user.getStatusesCount());
                ps.setLong(9, user.getFollowersCount());
                ps.setLong(10, user.getFriendsCount());
                return ps;
            }
        }, keyHolder);

        Long userId = keyHolder.getKey().longValue();

        List<Url> userUrls = user.getUrls();

        storeUrls(userUrls, userId, null);

        return userId;
    }

    public void storeUrls(List<Url> urlList, Long userId, Long tweetId) {

        // url's are associated to a user
        if(userId != null) {

            for (Url url : urlList) {

                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                        PreparedStatement ps = connection.prepareStatement(SQL_URL_CREATE);

                        ps.setString(1, url.getUrl());
                        ps.setString(2, url.getExpandedUrl());
                        ps.setString(3, url.getDisplayUrl());
                        ps.setLong(4, userId);
                        ps.setNull(5, Types.NULL);

                        return ps;
                    }
                });
            }
        }

        // url's are associated to a tweet
        if(tweetId != null) {

            for (Url url : urlList) {

                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                        PreparedStatement ps = connection.prepareStatement(SQL_URL_CREATE);

                        ps.setString(1, url.getUrl());
                        ps.setString(2, url.getExpandedUrl());
                        ps.setString(3, url.getDisplayUrl());
                        ps.setNull(4, Types.NULL);
                        ps.setLong(5, tweetId);

                        return ps;
                    }
                });
            }
        }
    }

    public long storeTweet(Tweet tweet, Long userId) {
        // Save userId in tweets table

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_TWEET_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, tweet.getTweetCreatedAt());
                ps.setLong(2, tweet.getTweetId());
                ps.setLong(3, (userId));
                ps.setString(4, tweet.getText());
                ps.setString(5, tweet.getSource());
                ps.setString(6, tweet.getLanguage());
                ps.setLong(7, tweet.getRetweetCount());
                ps.setLong(8, tweet.getFavoriteCount());
                ps.setBoolean(9, tweet.getRetweeted());
                ps.setBoolean(10, tweet.getFavorited());
                return ps;
            }
        }, keyHolder);

        long tweetId = keyHolder.getKey().longValue();

        return tweetId;
    }
}
