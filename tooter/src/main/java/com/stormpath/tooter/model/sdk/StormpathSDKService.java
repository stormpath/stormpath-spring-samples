package com.stormpath.tooter.model.sdk;

import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.ds.DataStore;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/14/12
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StormpathSDKService {

    Client getClient();

    String getTooterApplicationURL();

    String getAdministratorGroupURL();

    DataStore getDataStore();

    Directory getDirectory();

    String getDirectoryURL();

    Application getApplication();

}
