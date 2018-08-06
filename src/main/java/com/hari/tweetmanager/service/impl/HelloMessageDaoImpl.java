package com.hari.tweetmanager.service.impl;

import com.hari.tweetmanager.service.HelloMessageDao;
import org.springframework.stereotype.Service;

@Service
public class HelloMessageDaoImpl implements HelloMessageDao{

    public String getMessage() {
        return "Hello From Service";
    }

}
