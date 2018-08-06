package com.hari.tweetmanager.service;

public interface AuthDao {

    public String getOAuthToken();

    public String getoAuthVersion();

    public String getoAuthSignatureMethod();

    public String getOAuthConsumerKey();
}
