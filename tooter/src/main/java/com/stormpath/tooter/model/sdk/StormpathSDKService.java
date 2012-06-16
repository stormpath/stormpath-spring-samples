package com.stormpath.tooter.model.sdk;

import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.ds.DataStore;
import com.stormpath.sdk.tenant.Tenant;

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

    String getPremiumGroupURL();

    DataStore getDataStore();

    Directory getDirectory();

    Tenant getTenant();

    String getDirectoryURL();

}
