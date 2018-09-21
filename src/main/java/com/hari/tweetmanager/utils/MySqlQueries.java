package com.hari.tweetmanager.utils;

public class MySqlQueries {

    public static final String SQL_USER_CREATE =
            "insert into `users`(userId,name,screenName,location, description, userCreatedAt, " +
                    "favoritesCount,statusesCount,followersCount,friendsCount,language)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?)";

    public static final String SQL_URL_CREATE =
            "insert into `urls`(url,expandedUrl,displayUrl,userId, tweetId)" +
                    " values(?,?,?,?,?)";

    public static final String SQL_TWEET_CREATE =
            "insert into `tweets`(tweetCreatedAt,tweetId,userId,text, source, language, " +
                    "retweetCount,favoriteCount,favorited,retweeted,truncated)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?)";

    public static final String SQL_USER_GET_BY_ID = "select u.id, u.userId, u.name, u.screenName, u.location" +
            ", u.description, u.userCreatedAt, u.favoritesCount, u.statusesCount, u.followersCount" +
            ", u.friendsCount, u.language from `users` u where u.userId=?";

    public static final String SQL_LARGEST_TWEET_ID = "select max(tweetId) from tweets";

}
