package com.stormpath.tooter.model.sdk;

import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.ds.DataStore;
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
    private Client client;

    private DataStore dataStore;

    private Directory directory;

    private Application application;

    @Value("${stormpath.sdk.tooter.rest.url}")
    private String tooterApplicationURL;

    @Value("${stormpath.sdk.administrator.rest.url}")
    private String administratorGroupURL;

    @Value("${stormpath.sdk.premium.rest.url}")
    private String premiumGroupURL;

    @Value("${stormpath.sdk.tooter.directory.rest.url}")
    private String directoryURL;

    @Override
    public String getDirectoryURL() {
        return directoryURL;
    }

    @Override
    public String getAdministratorGroupURL() {
        return administratorGroupURL;
    }

    @Override
    public Application getApplication() {

        if (application == null) {
            this.application = getDataStore().load(getTooterApplicationURL(), Application.class);
        }

        return application;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public String getTooterApplicationURL() {
        return tooterApplicationURL;
    }

    @Override
    public DataStore getDataStore() {

        if (dataStore == null) {
            dataStore = client.getDataStore();
        }

        return dataStore;
    }

    @Override
    public Directory getDirectory() {

        if (directory == null) {
            directory = getDataStore().load(getDirectoryURL(), Directory.class);
        }

        return directory;
    }
}
