package com.hari.tweetmanager;

import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.TweetDao;
import com.hari.tweetmanager.service.impl.HelloMessageDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TweetManagerApplication implements CommandLineRunner {

	@Autowired
	private HelloMessageDaoImpl helloService;

	@Autowired
	private TweetDao tweetDao;

	public static void main(String[] args) {
		SpringApplication.run(TweetManagerApplication.class, args);
	}

	// Entry point of the application.

	@Override
	public void run(String... args) throws Exception {
		System.out.println(helloService.getMessage());

		tweetDao.getTweets();
	}
}
