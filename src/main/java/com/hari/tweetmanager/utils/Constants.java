package com.hari.tweetmanager.utils;

public class Constants {

    // These are the keys for records in memcached - they will be created after cap on number of requests for the API is reached.
    public static final String TIME_TILL_NEXT_REQUEST_KEY_TIMELINE = "timeTillNextRequestTimeline";

    public static final String TIME_TILL_NEXT_REQUEST_KEY_FAVORITE = "timeTillNextRequestFavorite";

    // Time to live for record that stores time at which next request can be made to timeline API
    public static final int MEMCACHE_TTL_TIMELINE_RECORD           = 900;

    // Time to live for record that stores time at which next request can be made to favorites API
    public static final int MEMCACHE_TTL_FAVORITE_RECORD           = 4500;
}
