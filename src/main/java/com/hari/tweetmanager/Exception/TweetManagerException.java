package com.hari.tweetmanager.Exception;

public class TweetManagerException extends  Exception {

    public TweetManagerException(String message) {
        super(message);
    }

    public TweetManagerException(Throwable cause) {
        super(cause);
    }

    public TweetManagerException(String message, Throwable cause) {
        super(message, cause);
    }

}
