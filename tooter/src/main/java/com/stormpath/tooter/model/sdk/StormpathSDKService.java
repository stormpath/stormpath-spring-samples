package com.stormpath.tooter.model.sdk;

import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.ds.DataStore;

/**
 * This service provides functionalities for communicating with the Stormpath API and using its SDK,
 * from the Tooter application.
 * <p/>
 * User: ecrisostomo
 * Date: 6/14/12
 * Time: 3:48 PM
 */
public interface StormpathSDKService {

    /**
     * Gets the Client object that interacts with the Stormpath API.
     *
     * @return the Client object that interacts with the Stormpath API.
     */
    Client getClient();

    /**
     * Gets the Rest URL of the Tooter application.
     *
     * @return the Rest URL of the Tooter application.
     */
    String getTooterApplicationURL();

    /**
     * Gets the Rest URL of the Administrator Group, which represents
     * the role of Administrator for an Account in the Tooter application.
     *
     * @return the Rest URL of the Administrator Group
     */
    String getAdministratorGroupURL();

    /**
     * Gets the DataStore from the Client object.
     *
     * @return the DataStore from the Client object.
     */
    DataStore getDataStore();

    /**
     * Gets the Directory, where the Tooter application is located, from the DataStore object.
     *
     * @return the Directory from the DataStore object.
     */
    Directory getDirectory();

    /**
     * Gets the Rest URL of the Directory where the Tooter application is located.
     *
     * @return the Rest URL of the Directory where the Tooter application is located.
     */
    String getDirectoryURL();

    /**
     * Gets the Tooter Application from the DataStore object.
     *
     * @return the Tooter Application from the DataStore object.
     */
    Application getApplication();

}
