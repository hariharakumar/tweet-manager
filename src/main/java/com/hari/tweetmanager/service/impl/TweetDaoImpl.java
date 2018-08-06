package com.hari.tweetmanager.service.impl;

import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.TweetDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetDaoImpl implements TweetDao {

    @Autowired
    AuthDao authDao;

    static Logger logger = Logger.getLogger(TweetDaoImpl.class);

    @Override
    public void getTweets() {
        String authToken = authDao.getOAuthToken();
        String oAuthConsumerKey = authDao.getOAuthConsumerKey();
        String oAuthSignatureMethod = authDao.getoAuthSignatureMethod();
        String oAuthVersion = authDao.getoAuthVersion();


    }
}
