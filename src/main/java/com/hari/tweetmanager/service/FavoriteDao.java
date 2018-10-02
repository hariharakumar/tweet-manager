package com.hari.tweetmanager.service;

import com.hari.tweetmanager.dto.Tweet;

import java.util.List;

public interface FavoriteDao {

    public List<Tweet> getFavoriteTweets();
}
