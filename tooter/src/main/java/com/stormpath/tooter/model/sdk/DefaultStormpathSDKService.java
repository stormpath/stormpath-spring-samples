package com.stormpath.tooter.model.sdk;

import com.stormpath.sdk.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/14/12
 * Time: 3:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DefaultStormpathSDKService implements StormpathSDKService {

    @Autowired
    private Client stormpathSDKClient;

    @Value("${stormpath.sdk.rest.url}")
    private String restURL;

    public Client getStormpathSDKClient() {
        return stormpathSDKClient;
    }

    public String getRestURL() {
        return restURL;
    }
}
