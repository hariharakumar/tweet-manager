package com.hari.tweetmanager;

import com.amazonaws.util.json.JSONArray;
import com.hari.tweetmanager.dto.Tweet;
import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.TweetDao;
import com.hari.tweetmanager.service.impl.HelloMessageDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@SpringBootApplication
@PropertySource("file:/var/personal_projects/tweet_manager/application.properties")
public class TweetManagerApplication implements CommandLineRunner {

	@Autowired
	private HelloMessageDaoImpl helloService;

	@Autowired
	private TweetDao tweetDao;

	public static void main(String[] args) {
		SpringApplication.run(TweetManagerApplication.class, args);
	}

	// Entry point of the application.
	/* TODO : Use the spring way to load properties directly from property file instead of using env
	   Ex : @Value("${jwt.secret.key}")
  			private String jwtSecretKey;
  	   //TODO : Write signature definitions to each method - using /** and then enter
  	*/

	@Override
	public void run(String... args) throws Exception {
		System.out.println(helloService.getMessage());

		tweetDao.getTweets(5);

		// Retrieving tweets using timeline concept to GET tweets
		List<Tweet> tweets = tweetDao.getTweets();

		tweetDao.storeTweetsInDatabase(tweets);
	}
}
