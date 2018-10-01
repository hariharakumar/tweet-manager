package com.hari.tweetmanager.service;

import com.hari.tweetmanager.Exception.TweetManagerException;
import com.hari.tweetmanager.dto.Url;

import java.util.List;

public interface UrlDao {

    public void storeUrls(List<Url> urlList, Long userId, Long tweetId)  throws TweetManagerException;
}
