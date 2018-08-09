package com.hari.tweetmanager.service.impl;

import com.hari.tweetmanager.service.AuthDao;
import com.hari.tweetmanager.service.TweetDao;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.UriBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;

@Service
public class TweetDaoImpl implements TweetDao {

    @Autowired
    AuthDao authDao;

    static Logger logger = Logger.getLogger(TweetDaoImpl.class);

    @Override
    public void getTweets() {

        String httpMethod = "GET";
        String requestBaseUrl = "https://api.twitter.com/1.1/statuses/home_timeline.json";
        //TODO : URL Encode query parameters
        HashMap queryParams = new HashMap();
        queryParams.put("count","5");
        String authHeader = authDao.getAuthHeader(httpMethod, requestBaseUrl, queryParams);

        Client client = Client.create();
        URI baseURI = UriBuilder.fromUri(requestBaseUrl).build();
        WebResource webResource = client.resource(baseURI);

        try {

            String encodedQueryParams = URLEncoder.encode("count=5", "UTF-8");

            String response = webResource.queryParam("count","5")
                              .header("Authorization", authHeader)
                              .header("User-Agent","OAuth gem v0.4.4")
                              .header("Host","api.twitter.com")
                              .get(String.class);
            System.out.println("Response from Twitter API : " + response);
        }
        catch (UnsupportedEncodingException use) {
            logger.error("Unsupported encoding : " + use.getMessage());
        }


    }
}
