package com.hari.tweetmanager.service.impl;

import com.hari.tweetmanager.service.AuthDao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("file:/var/personal_projects/tweet_manager/application.properties")
public class AuthDaoImpl implements AuthDao {

    static Logger logger = Logger.getLogger(AuthDaoImpl.class);

    private final String oAuthVersion = "1.0";
    private final String oAuthSignatureMethod = "HMAC-SHA1";

    @Autowired
    private Environment env;

    @Override
    public String getoAuthVersion() {
        return oAuthVersion;
    }

    @Override
    public String getoAuthSignatureMethod() {
        return oAuthSignatureMethod;
    }

    @Override
    public String getOAuthToken() {

        String oAuthToken = env.getProperty("oauth.token");

        System.out.println("oAuthToken : " + oAuthToken);

        return oAuthToken;
    }

    @Override
    public String getOAuthConsumerKey() {

        String oAuthConsumerKey = env.getProperty("oauth.consumer.key");

        System.out.println("oAuthConsumerKey : " + oAuthConsumerKey);

        return oAuthConsumerKey;
    }


}
