package com.hari.tweetmanager.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class StringUtils {

    public static String get32ByteRandomString() {
        byte[] bytes = new byte[32];

        try {
            SecureRandom.getInstanceStrong().nextBytes(bytes);
        }
        catch (NoSuchAlgorithmException nse) {
            DateTimeUtils.logger.error("Unable to build Random String : " , nse);
        }

        return bytes.toString();
    }

}
