package com.hari.tweetmanager.service;

import com.amazonaws.util.json.JSONArray;
import com.hari.tweetmanager.dto.Tweet;

import java.util.List;

public interface TweetDao {

    public void getTweets(int count);

    public List<Tweet> getTweets();

    public void storeTweetsInDatabase(List<Tweet> tweets);

    public Long getLargestTweetIdInDb();
}
