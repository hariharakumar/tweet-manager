package com.hari.tweetmanager.utils;

import com.hari.tweetmanager.Exception.TweetManagerException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HttpUtils {

    static Logger logger = Logger.getLogger(HttpUtils.class);

    public static String sendGetRequest(String requestBaseUrl, HashMap<String, String> queryParams, String authHeader)
                    throws UnsupportedEncodingException, TweetManagerException {
        Client client = Client.create();
        URI baseURI = UriBuilder.fromUri(requestBaseUrl).build();
        WebResource webResource = client.resource(baseURI);
        MultivaluedMap<String, String> queryParameters = new MultivaluedMapImpl();
        List<Integer> allowedStatuses = Arrays.asList(Response.Status.OK.getStatusCode(), Response.Status.NOT_FOUND.getStatusCode());

        // URL Encode the query parameters
        for (String key : queryParams.keySet()) {
            String urlEncodedKey = URLEncoder.encode(key, "UTF-8");
            String urlEncodedValue = URLEncoder.encode(queryParams.get(key), "UTF-8");

            queryParameters.putSingle(urlEncodedKey, urlEncodedValue);
        }

        ClientResponse response = webResource.queryParams(queryParameters)
                            .header("Authorization", authHeader)
                            .header("User-Agent", "OAuth gem v0.4.4")
                            .header("Host", "api.twitter.com")
                            .get(ClientResponse.class);

        String responseData = response.getEntity(String.class);

        logger.info("GET Request sent. Response Status : " +
                response.getStatus() + " , Response " + responseData);

        if(!allowedStatuses.contains(response.getStatus())) {
            throw new TweetManagerException("Received response : " + response.getStatus() + " on a GET request");
        }

        return responseData;
    }



}
