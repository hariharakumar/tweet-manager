package com.hari.tweetmanager.service.impl;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.hari.tweetmanager.Exception.TweetManagerException;
import com.hari.tweetmanager.dto.Tweet;
import com.hari.tweetmanager.mapper.TweetMapper;
import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.FavoriteDao;
import com.hari.tweetmanager.utils.Constants;
import com.hari.tweetmanager.utils.DateTimeUtils;
import com.hari.tweetmanager.utils.HttpUtils;
import com.sun.jersey.api.client.ClientResponse;
import net.spy.memcached.MemcachedClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FavoriteDaoImpl implements FavoriteDao {

    static Logger logger = Logger.getLogger(FavoriteDaoImpl.class);

    @Autowired
    MemcachedClient memcachedClient;

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
        List<Tweet> favoriteTweets = new ArrayList<>();

        // Query memcache to check if we can make another request
        Long timeTillNextRequest = (Long) memcachedClient.get(Constants.TIME_TILL_NEXT_REQUEST_KEY_FAVORITE);
        Long currentTimeInEpoch = DateTimeUtils.getCurrentTimeInSecondsInEpoch();

        if (timeTillNextRequest != null && currentTimeInEpoch < timeTillNextRequest) {
            logger.info("You can only make 75 requests in 75 minutes interval to timeline API. Don't make any requests for next : "
                    + (timeTillNextRequest - currentTimeInEpoch) + " seconds");
            return favoriteTweets;
        }

        try {

            String authHeader = authDao.getAuthHeader(httpMethod, requestBaseUrl, queryParams);

            ClientResponse tweetResponse = HttpUtils.sendGetRequest(requestBaseUrl, queryParams, authHeader);

            Long requestsLeft = Long.parseLong(tweetResponse.getHeaders().get("x-rate-limit-remaining").get(0));

            // Standard API's can make 75 requests in 75 minutes time window to favorites API
            if(requestsLeft == 0) {
                Long timeTillNextRequestInEpoch = Long.parseLong(tweetResponse.getHeaders().get("x-rate-limit-reset").get(0));

                logger.info("Do not send any more requests to twitter favorites API for " +
                        (timeTillNextRequestInEpoch - DateTimeUtils.getCurrentTimeInSecondsInEpoch()) + " seconds. 15 requests per 15 minutes cap reached");

                memcachedClient.set(Constants.TIME_TILL_NEXT_REQUEST_KEY_FAVORITE, Constants.MEMCACHE_TTL_TIMELINE_RECORD, timeTillNextRequestInEpoch);
            }

            String tweetData = tweetResponse.getEntity(String.class);

            JSONArray tweetsFromCurrentRequest = new JSONArray(tweetData);

            logger.debug("Retrieved last" + tweetsFromCurrentRequest.length() + " tweets that are favorited by me");

            for (int i = 0; i < tweetsFromCurrentRequest.length(); i++) {

                JSONObject tweetsJSONObject = tweetsFromCurrentRequest.getJSONObject(i);
                logger.trace("Id of the tweet : " + tweetsJSONObject.getLong("id"));

                Tweet tweet = tweetMapper.convertToTweetObject(tweetsJSONObject);

                favoriteTweets.add(tweet);
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

        return favoriteTweets;
    }
}
