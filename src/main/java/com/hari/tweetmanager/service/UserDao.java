package com.hari.tweetmanager.service;

import com.hari.tweetmanager.Exception.TweetManagerException;
import com.hari.tweetmanager.dto.User;

public interface UserDao {

    public long storeUser(User user) throws TweetManagerException;

    public User getUserById(Long userId);
}
