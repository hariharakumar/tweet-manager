package com.hari.tweetmanager.service;

import com.hari.tweetmanager.dto.User;

public interface UserDao {

    public long storeUser(User user);

    public User getUserById(Long userId);
}
