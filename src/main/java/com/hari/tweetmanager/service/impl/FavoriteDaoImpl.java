package com.hari.tweetmanager.service.impl;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.hari.tweetmanager.Exception.TweetManagerException;
import com.hari.tweetmanager.dto.Tweet;
import com.hari.tweetmanager.mapper.TweetMapper;
import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.FavoriteDao;
import com.hari.tweetmanager.utils.DateTimeUtils;
import com.hari.tweetmanager.utils.HttpUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.UriBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FavoriteDaoImpl implements FavoriteDao {

    static Logger logger = Logger.getLogger(FavoriteDaoImpl.class);

    @Autowired
    AuthDao authDao;

    @Autowired
    TweetMapper tweetMapper;

    /**
     * @return : Returns the 100 most recent Tweets liked by the authenticating or specified user.
     */
    @Override
    public List<Tweet> getFavoriteTweets() {

        String httpMethod = "GET";
        String requestBaseUrl = "https://api.twitter.com/1.1/favorites/list.json";
        int count = 100;

        HashMap queryParams = new HashMap();
        queryParams.put("count", String.valueOf(count));
        List<Tweet> tweets = new ArrayList<>();

        try {

            String authHeader = authDao.getAuthHeader(httpMethod, requestBaseUrl, queryParams);

            ClientResponse tweetResponse = HttpUtils.sendGetRequest(requestBaseUrl, queryParams, authHeader);

            Long requestsLeft = Long.parseLong(tweetResponse.getHeaders().get("x-rate-limit-remaining").get(0));

            // Standard API's can only make 15 requests in 15 minutes time window
            if(requestsLeft == 0) {
                Long remainingTimeInEpochSecs = Long.parseLong(tweetResponse.getHeaders().get("x-rate-limit-reset").get(0));

                logger.info("Do not send any more requests to twitter API for " +
                        (remainingTimeInEpochSecs - DateTimeUtils.getCurrentTimeInSecondsInEpoch()) + " seconds. 15 requests per 15 minutes cap reached");
            }

            String tweetData = tweetResponse.getEntity(String.class);

            JSONArray tweetsFromCurrentRequest = new JSONArray(tweetData);

            logger.debug("Got " + tweetsFromCurrentRequest.length() + " tweets favorited by me");

            for (int i = 0; i < tweetsFromCurrentRequest.length(); i++) {

                JSONObject tweetsJSONObject = tweetsFromCurrentRequest.getJSONObject(i);
                logger.debug("Id of the tweet : " + tweetsJSONObject.getLong("id"));

                Tweet tweet = tweetMapper.convertToTweetObject(tweetsJSONObject);

                tweets.add(tweet);
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
        }

        return tweets;
    }
}
