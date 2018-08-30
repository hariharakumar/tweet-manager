package com.hari.tweetmanager.utils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;

public class HttpUtils {

    public static String sendGetRequest(String requestBaseUrl, HashMap<String, String> queryParams, String authHeader)
                    throws UnsupportedEncodingException {
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

        String response = webResource.queryParams(queryParameters)
                            .header("Authorization", authHeader)
                            .header("User-Agent", "OAuth gem v0.4.4")
                            .header("Host", "api.twitter.com")
                            .get(String.class);
        return response;

    }

}
