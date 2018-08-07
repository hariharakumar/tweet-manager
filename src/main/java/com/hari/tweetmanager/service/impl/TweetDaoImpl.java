package com.hari.tweetmanager.service.impl;

import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.TweetDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TweetDaoImpl implements TweetDao {

    @Autowired
    AuthDao authDao;

    static Logger logger = Logger.getLogger(TweetDaoImpl.class);

    @Override
    public void getTweets() {

        String httpMethod = "GET";
        String requestBaseUrl = "https://api.twitter.com/1.1/statuses/home_timeline.json";
        //TODO : URL Encode query parameters
        HashMap queryParams = new HashMap();
        queryParams.put("count","5");
        String oAuthSignature = authDao.getAuthHeader(httpMethod, requestBaseUrl, queryParams);

        // Use the oAuthSignature to make the actual request


    }
}
