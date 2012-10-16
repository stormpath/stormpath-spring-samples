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
package com.stormpath.tooter.util;

import com.stormpath.tooter.model.User;
import com.stormpath.tooter.model.sdk.StormpathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Elder Crisostomo
 */
@Component
public class PermissionUtil {

    @Value("${stormpath.sdk.administrator.rest.url}")
    private String administratorGroupURL;

    @Value("${stormpath.sdk.premium.rest.url}")
    private String premiumGroupURL;

    @Autowired
    StormpathService stormpath;

    // Temporarily doing a string based to the expression language limitations with enums
    public boolean hasRole(User user, String role) {
        boolean hasRole = false;

        if (user.getAccount().getGroupMemberships().iterator().hasNext()) {
            String groupHref = user.getAccount().getGroupMemberships().iterator().next().getGroup().getHref();
            if (role.equalsIgnoreCase("ADMINISTRATOR")) {
                hasRole = groupHref.equals(administratorGroupURL);
            } else if (role.equalsIgnoreCase("PREMIUM_USER")) {
                hasRole = groupHref.equals(premiumGroupURL);
            }
        }

        return hasRole;
    }
}
