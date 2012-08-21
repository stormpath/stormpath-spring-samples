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
import com.stormpath.sdk.tenant.Tenant;

/**
 * The Stormpath SDK can be used to interact with anything in a Stormpath tenant.
 * <p/>
 * This service provides an even simpler abstraction specific to Tooter's needs only.
 *
 * @author Elder Crisostomo
 */
public interface StormpathService {

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
     * Returns the Tenant that owns the Tooter application.
     *
     * @return the Tenant that owns the Tooter application.
     */
    Tenant getTenant();

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
