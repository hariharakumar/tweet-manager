package com.hari.tweetmanager.service;

import java.util.HashMap;

public interface AuthDao {

    public String getAuthHeader(String httpMethod, String requestUrl, HashMap<String, String> queryParams);
}
