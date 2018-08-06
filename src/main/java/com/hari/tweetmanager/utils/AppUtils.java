package com.hari.tweetmanager.utils;

import org.apache.log4j.Logger;;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AppUtils {

    static Logger logger = Logger.getLogger(AppUtils.class);

    public String get32ByteRandomString() {
        byte[] bytes = new byte[32];

        try {
            SecureRandom.getInstanceStrong().nextBytes(bytes);
        }
        catch (NoSuchAlgorithmException nse) {
            logger.error("Unable to build Random String : " + nse.getMessage());
        }

        return bytes.toString();
    }

    public Long getCurrentTimeInSecondsInEpoch() {
        return System.currentTimeMillis() / 1000L;
    }
}
