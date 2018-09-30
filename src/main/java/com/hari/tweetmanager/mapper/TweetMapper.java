package com.hari.tweetmanager.mapper;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.hari.tweetmanager.dto.Tweet;
import com.hari.tweetmanager.dto.Url;
import com.hari.tweetmanager.dto.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetMapper {

    public Tweet convertToTweetObject(JSONObject inputTweet) throws JSONException {

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

    public List<Url> getUrls(JSONArray urlArray) throws JSONException {

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

    public User getUser(JSONObject userObjectInTweet) throws JSONException {

        User user = new User();
        List<Url> urls = new ArrayList<>();

        user.setUserId(userObjectInTweet.getLong("id"));

        /* TODO : Before building entire user object, check to see if user already exists in DB - If user already exists
           there is no need to create the user object again */

        user.setName(userObjectInTweet.getString("name"));
        user.setScreenName(userObjectInTweet.getString("screen_name"));
        user.setLocation(userObjectInTweet.getString("location"));
        user.setDescription(userObjectInTweet.getString("description"));

        JSONObject userEntities = userObjectInTweet.getJSONObject("entities");

        if(userEntities.has("url") && userEntities.getJSONObject("url") != null &&
                (userEntities.getJSONObject("url").getJSONArray("urls")).length() > 0) {
            JSONArray urlArray = userEntities.getJSONObject("url").getJSONArray("urls");

            urls = getUrls(urlArray);
        }

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
