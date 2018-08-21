package com.hari.tweetmanager.utils;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DateTimeUtils {

    static Logger logger = Logger.getLogger(DateTimeUtils.class);

    public static Long getCurrentTimeInSecondsInEpoch() {
        return System.currentTimeMillis() / 1000L;
    }

    public static String convertToRFC3339Format(String dateTime) throws ParseException {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.parse(dateTime).toString();
    }
}
