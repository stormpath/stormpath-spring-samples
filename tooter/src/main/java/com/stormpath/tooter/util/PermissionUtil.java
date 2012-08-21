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

import com.stormpath.tooter.model.sdk.StormpathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Elder Crisostomo
 */
@Component
public class PermissionUtil {

    public static final String REMOVE_TOOT_PERMISSION = "remove.toot";

    private Map<String, List<String>> permissionsMap;

    @Autowired
    StormpathService stormpath;

    @PostConstruct
    void init() {
        permissionsMap = new HashMap<String, List<String>>();
        List<String> groups = new ArrayList<String>();

        groups.add(stormpath.getAdministratorGroupURL());
        permissionsMap.put(REMOVE_TOOT_PERMISSION, groups);
    }

    @PreDestroy
    void unload() {
        permissionsMap.clear();
        permissionsMap = null;
    }

    public boolean isGroupAllowed(String permissionId, Map<String, String> groupMap) {

        boolean isGroupAllowed = false;

        if (permissionId != null && groupMap != null) {

            if (permissionsMap.containsKey(permissionId)) {

                List<String> permissionGroupURLs = permissionsMap.get(permissionId);

                outer:
                for (String group : permissionGroupURLs) {

                    if (groupMap.containsKey(group)) {

                        isGroupAllowed = true;
                        break;
                    } else {

                        for (String key : groupMap.keySet()) {

                            if (key.contains(group)) {
                                isGroupAllowed = true;
                                break outer;
                            }
                        }
                    }
                }
            }
        }

        return isGroupAllowed;
    }
}
