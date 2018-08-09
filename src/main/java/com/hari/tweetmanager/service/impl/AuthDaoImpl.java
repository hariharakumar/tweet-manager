package com.hari.tweetmanager.service.impl;

import com.hari.tweetmanager.service.AuthDao;

import com.hari.tweetmanager.utils.AppUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Set;

@Service
@PropertySource("file:/var/personal_projects/tweet_manager/application.properties")
public class AuthDaoImpl implements AuthDao {

    static Logger logger = Logger.getLogger(AuthDaoImpl.class);

    private final String oAuthVersion = "1.0";
    private final String oAuthSignatureMethod = "HMAC-SHA1";

    @Autowired
    private Environment env;

    public String getAuthHeader(String httpMethod, String requestBaseUrl, HashMap<String, String> queryParams) {

        String oAuthConsumerKey          = env.getProperty("oauth.consumer.key");
        String oAuthToken                = env.getProperty("oauth.token");
        String random32ByteString        = AppUtils.get32ByteRandomString();
        Long currentTimeInSecondsInEpoch = AppUtils.getCurrentTimeInSecondsInEpoch();

        String consumerSecret            = env.getProperty("oauth.consumer.secret");
        String oAuthTokenSecret          = env.getProperty("oauth.token.secret");

        StringBuilder headerString        = new StringBuilder();
        StringBuilder encodeQueryParams   = new StringBuilder();
        StringBuilder parameterString     = new StringBuilder();
        StringBuilder signatureBaseString = new StringBuilder();

        Set<String> queryParamsKeys = queryParams.keySet();

        try {

            String oAuthConsumerKeyString     = URLEncoder.encode("oauth_consumer_key","UTF-8");
            String oAuthNonceString           = URLEncoder.encode("oauth_nonce","UTF-8");
            String oAuthSignatureKeyString    = URLEncoder.encode("oauth_signature","UTF-8");
            String oAuthSignatureMethodString = URLEncoder.encode("oauth_signature_method","UTF-8");
            String oAuthTimeStampString       = URLEncoder.encode("oauth_timestamp","UTF-8");
            String oAuthTokenString           = URLEncoder.encode("oauth_token","UTF-8");
            String oAuthVersionString         = URLEncoder.encode("oauth_version","UTF-8");

            String encodedConsumerKey          = URLEncoder.encode(oAuthConsumerKey,"UTF-8");
            String encodedNonceString          = URLEncoder.encode(random32ByteString,"UTF-8");
            String encodedOAuthSignatureMethod = URLEncoder.encode(oAuthSignatureMethod,"UTF-8");
            String encodedEpochTime            = URLEncoder.encode(String.valueOf(currentTimeInSecondsInEpoch),"UTF-8");
            String encodedOAuthToken           = URLEncoder.encode(oAuthToken,"UTF-8");
            String encodedOAuthVersion         = URLEncoder.encode(oAuthVersion,"UTF-8");

            String singleQuote                 = "\"";
            String commaAndSpace               = ", ";


            for (String key : queryParamsKeys) {
                encodeQueryParams.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(queryParams.get(key), "UTF-8")).append("&");
            }

            // There will be an & at the end of queryParamsString
            parameterString.append(encodeQueryParams).
                            append(oAuthConsumerKeyString + "=" + encodedConsumerKey).append("&").
                            append(oAuthNonceString + "=" + encodedNonceString).append("&").
                            append(oAuthSignatureMethodString + "=" + encodedOAuthSignatureMethod).append("&").
                            append(oAuthTimeStampString + "=" + encodedEpochTime).append("&").
                            append(oAuthTokenString + "=" + encodedOAuthToken).append("&").
                            append(oAuthVersionString + "=" + encodedOAuthVersion);

            // Signature base string should contain only 2 &
            signatureBaseString.append(httpMethod.toUpperCase()).append("&").
                                append(URLEncoder.encode(requestBaseUrl,"UTF-8")).append("&").
                                append(URLEncoder.encode(parameterString.toString(),"UTF-8"));

            String signingKey = URLEncoder.encode(consumerSecret,"UTF-8") + "&" + URLEncoder.encode(oAuthTokenSecret,"UTF-8");

            // Combine signatureBaseString and signingKey to generate the Auth header
            Mac macSha1 = Mac.getInstance("HmacSHA1");

            macSha1.init(new SecretKeySpec(signingKey.getBytes(), "HmacSHA1"));
            macSha1.update(signatureBaseString.toString().getBytes());

            byte[] res                   = macSha1.doFinal();
            String encodedOAuthSignature = URLEncoder.encode(String.valueOf(Base64Coder.encode(res)),"UTF-8");

            // Append all 7 keys to final headerString
            headerString.append("OAuth ").
                         append(oAuthConsumerKeyString).append("=").append(singleQuote).append(encodedConsumerKey).append(singleQuote).append(commaAndSpace).
                         append(oAuthNonceString).append("=").append(singleQuote).append(encodedNonceString).append(singleQuote).append(commaAndSpace).
                         append(oAuthSignatureKeyString).append("=").append(singleQuote).append(encodedOAuthSignature).append(singleQuote).append(commaAndSpace).
                         append(oAuthSignatureMethodString).append("=").append(singleQuote).append(encodedOAuthSignatureMethod).append(singleQuote).append(commaAndSpace).
                         append(oAuthTimeStampString).append("=").append(singleQuote).append(encodedEpochTime).append(singleQuote).append(commaAndSpace).
                         append(oAuthTokenString).append("=").append(singleQuote).append(encodedOAuthToken).append(singleQuote).append(commaAndSpace).
                         append(oAuthVersionString).append("=").append(singleQuote).append(encodedOAuthVersion).append(singleQuote);

        }
        catch (UnsupportedEncodingException use) {
            logger.error("Error while encoding query params : " + use.getMessage());
        }
        catch (NoSuchAlgorithmException nse) {
            logger.error("HMAC Algorithm cannot be foudn : " + nse.getMessage());
        }
        catch (InvalidKeyException ike) {
            logger.error("Invalid Key Exception : " + ike.getMessage());
        }

        return headerString.toString();
    }


}
