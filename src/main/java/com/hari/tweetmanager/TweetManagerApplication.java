package com.hari.tweetmanager;

import com.amazonaws.util.json.JSONArray;
import com.hari.tweetmanager.dto.Tweet;
import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.FavoriteDao;
import com.hari.tweetmanager.service.TweetDao;
import com.hari.tweetmanager.service.impl.HelloMessageDaoImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@SpringBootApplication
@PropertySource("file:/var/personal_projects/tweet_manager/application.properties")
public class TweetManagerApplication implements CommandLineRunner {

	static Logger logger = Logger.getLogger(TweetManagerApplication.class);

	@Autowired
	private HelloMessageDaoImpl helloService;

	@Autowired
	private TweetDao tweetDao;

	@Autowired
	private FavoriteDao favoriteDao;

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
		logger.debug( "Testing spring autowired annotation " + helloService.getMessage());

		// Retrieving tweets using twitter timeline concept
		List<Tweet> tweets = tweetDao.getTweets();

		tweetDao.storeTweetsInDatabase(tweets);

		logger.debug("Done storing tweets in database");

		favoriteDao.getFavoriteTweets();
	}
}
