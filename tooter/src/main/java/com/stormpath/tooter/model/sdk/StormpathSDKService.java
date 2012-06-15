package com.stormpath.tooter.model.sdk;

import com.stormpath.sdk.client.Client;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/14/12
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StormpathSDKService {

    Client getStormpathSDKClient();

    String getRestURL();

}
