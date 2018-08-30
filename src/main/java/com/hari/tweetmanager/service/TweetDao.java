package com.hari.tweetmanager.service;

import com.amazonaws.util.json.JSONArray;

public interface TweetDao {

    public void getTweets(int count);

    public JSONArray getTweets();

    public void storeTweetsInDatabase(JSONArray tweets);
}
