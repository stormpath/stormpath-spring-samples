/*
 * Copyright 2012 Stormpath, Inc. and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.tooter.model.sdk;

import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.ds.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Elder Crisostomo
 */
@Service
public class DefaultStormpathSDKService implements StormpathSDKService {

    @Resource(name = "sdkClient")
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
            application = getDataStore().getResource(getTooterApplicationURL(), Application.class);
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
            dataStore = getClient().getDataStore();
        }

        return dataStore;
    }

    @Override
    public Directory getDirectory() {

        if (directory == null) {
            directory = getDataStore().getResource(getDirectoryURL(), Directory.class);
        }

        return directory;
    }
}
