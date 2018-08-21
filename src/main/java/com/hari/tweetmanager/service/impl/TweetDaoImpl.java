package com.hari.tweetmanager.service.impl;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.hari.tweetmanager.dto.Tweet;
import com.hari.tweetmanager.dto.Url;
import com.hari.tweetmanager.dto.User;
import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.TweetDao;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TweetDaoImpl implements TweetDao {

    @Autowired
    AuthDao authDao;

    static Logger logger = Logger.getLogger(TweetDaoImpl.class);

    @Override
    public void getTweets(int count) {

        String httpMethod = "GET";
        String requestBaseUrl = "https://api.twitter.com/1.1/statuses/home_timeline.json";

        HashMap queryParams = new HashMap();
        queryParams.put("count", String.valueOf(count));
        String authHeader = authDao.getAuthHeader(httpMethod, requestBaseUrl, queryParams);

        Client client = Client.create();
        URI baseURI = UriBuilder.fromUri(requestBaseUrl).build();
        WebResource webResource = client.resource(baseURI);

        try {
            // URLEncoding is not necessary here as there are no special chars in key or value of query params
            String tweetData = webResource.queryParam("count",String.valueOf(count))
                              .header("Authorization", authHeader)
                              .header("User-Agent","OAuth gem v0.4.4")
                              .header("Host","api.twitter.com")
                              .get(String.class);
            System.out.println("Response from Twitter API : " + tweetData);

            JSONArray tweets = new JSONArray(tweetData);
            logger.info("Tweet Data returned : " + tweets.toString());

        }
        catch (JSONException jse) {
            logger.error("JSON Parse Exception : " , jse);
        }
    }

    /* Use max_id and since_id  for fetching tweet data
       *max_id* : an application’s first request to a timeline endpoint should only specify a count.
                When processing this and subsequent responses, keep track of the lowest ID received.
                This ID should be passed as the value of the max_id parameter for the next request, which will only return Tweets with IDs lower than or equal to the value of the max_id parameter.
       *since_id* : greatest ID of all the Tweets your application has already processed
       Applications which use both the max_id and since_id parameters correctly minimize the amount of redundant data they fetch and process,
       while retaining the ability to iterate over the entire available contents of a timeline.
        */
    @Override
    public void getTweets() {

        /*Steps to use max_id and since_id :
        * Make a first request just by using count - Keep track of the lowest id of the tweets received
        * Make another requests by passing max_id - this value will be (lowestId - 1) you gathered above - again keep track of lowest id of tweets received
        * Make further requests by passing max_id as (lowestTweetId - 1) received in previous requests - Keep doing it.
        *
        * since_id :
        * You process the tweets in batches : After processing a batch, keep track of highest tweetId received :
        * When processing next batch use the highestTweetId that you got above and use it as since_id for every request in next batch
        * Repeat the same by passing since_id as the highestTweetId that is received in previous batch
        *
        * More Info : https://developer.twitter.com/en/docs/tweets/timelines/guides/working-with-timelines
        * */

        String httpMethod = "GET";
        String requestBaseUrl = "https://api.twitter.com/1.1/statuses/home_timeline.json";

        /* Get the tweets from database - if there aren't any -> get 200 tweets and store it in database
           If you find some tweets, get the max tweetId -> use it as since_id for next request to get tweets
           THERE WON'T BE ANY SLEEP LOGIC -> EACH TIME YOU RUN THE APPLICATION, IT GETS TWEETS AND STORES IT IN DB
           each time you run get 5 tweets at a time and use max_id logic to avoid getting duplicate tweets
            */

        HashMap queryParams = new HashMap();
        queryParams.put("count", String.valueOf("10"));

        try {

            String tweetData = sendGetRequest(requestBaseUrl, queryParams, httpMethod);

            JSONArray tweets = new JSONArray(tweetData);
            System.out.println("Tweets Count : " + tweets.length());
        }
        catch (JSONException jse) {
            logger.error("JSON Parse Exception : ", jse);
        }

    }

    private String sendGetRequest(String requestBaseUrl, HashMap<String, String> queryParams, String httpMethod) {

        String authHeader = authDao.getAuthHeader(httpMethod, requestBaseUrl, queryParams);
        String tweetData = null;

        try {

            Client client = Client.create();
            URI baseURI = UriBuilder.fromUri(requestBaseUrl).build();
            WebResource webResource = client.resource(baseURI);
            MultivaluedMap queryParameters = new MultivaluedMapImpl();

            // URL Encode the query parameters
            for (String key : queryParams.keySet()) {
                String urlEncodedKey = URLEncoder.encode(key, "UTF-8");
                String urlEncodedValue = URLEncoder.encode(queryParams.get(key), "UTF-8");

                queryParameters.putSingle(urlEncodedKey, urlEncodedValue);
            }

            tweetData = webResource.queryParams(queryParameters)
                    .header("Authorization", authHeader)
                    .header("User-Agent", "OAuth gem v0.4.4")
                    .header("Host", "api.twitter.com")
                    .get(String.class);
        }
        catch (UnsupportedEncodingException use) {
            logger.error("Error while encoding data : ", use);
        }

        return tweetData;
    }

    private void storeTweetsInDatabase(JSONArray tweets) {

        for(int i=0 ; i< tweets.length(); i++) {

            try {
                JSONObject tweetsJSONObject = tweets.getJSONObject(i);

                Tweet tweet      = getTweetData(tweetsJSONObject);

            }
            catch (JSONException jse) {
                logger.error("Error while parsing tweet json object" , jse);
            }
        }


    }

    private Tweet getTweetData(JSONObject inputTweet) throws JSONException {

        Tweet tweet = new Tweet();

        tweet.setTweetCreatedAt(inputTweet.getString("created_at"));
        tweet.setTweetId(inputTweet.getLong("id"));
        tweet.setText(inputTweet.getString("text"));
        tweet.setSource(inputTweet.getString("source"));
        tweet.setLanguage(inputTweet.getString("lang"));

        JSONArray urlArray = inputTweet.getJSONObject("entities").getJSONArray("urls");
        List<Url> urls = getUrls(urlArray);
        tweet.setUrls(urls);

        JSONObject userObjectInTweet = inputTweet.getJSONObject("user");
        User user = getUser(userObjectInTweet);
        tweet.setUser(user);

        tweet.setRetweetCount(inputTweet.getLong("retweet_count"));
        tweet.setFavoriteCount(inputTweet.getLong("favorite_count"));
        tweet.setFavorited(inputTweet.getBoolean("favorited"));
        tweet.setRetweeted(inputTweet.getBoolean("retweeted"));
        tweet.setTruncated(inputTweet.getBoolean("truncated"));

        return tweet;
    }

    private List<Url> getUrls(JSONArray urlArray) throws JSONException {

        List<Url> urls = new ArrayList<>();

        for(int i=0; i < urlArray.length(); i++) {

            Url url = new Url();
            JSONObject urlObject = urlArray.getJSONObject(i);

            url.setUrl(urlObject.getString("url"));
            url.setDisplayUrl(urlObject.getString("display_url"));
            url.setExpandedUrl(urlObject.getString("expanded_url"));

            urls.add(url);
        }

        return urls;
    }

    private User getUser(JSONObject userObjectInTweet) throws JSONException {

        User user = new User();

        user.setUserId(userObjectInTweet.getLong("id"));

        /* TODO : Before building entire user object, check to see if user already exists in DB - If user already exists
           there is no need to create the user object again */

        user.setName(userObjectInTweet.getString("name"));
        user.setScreenName(userObjectInTweet.getString("screen_name"));
        user.setLocation(userObjectInTweet.getString("location"));
        user.setDescription(userObjectInTweet.getString("description"));

        JSONArray urlArray = userObjectInTweet.getJSONObject("entities").getJSONObject("url").getJSONArray("urls");
        List<Url> urls = getUrls(urlArray);

        user.setUrls(urls);

        user.setUserCreatedAt(userObjectInTweet.getString("created_at"));
        user.setFavoritesCount(userObjectInTweet.getLong("favourites_count"));
        user.setStatusesCount(userObjectInTweet.getLong("statuses_count"));
        user.setFollowersCount(userObjectInTweet.getLong("followers_count"));
        user.setFriendsCount(userObjectInTweet.getLong("friends_count"));
        user.setLanguage(userObjectInTweet.getString("lang"));

        return user;
    }


}
