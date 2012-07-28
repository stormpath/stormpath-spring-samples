package com.stormpath.tooter.util;

import com.stormpath.tooter.model.sdk.StormpathSDKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/15/12
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PermissionUtil {

    public static final String REMOVE_TOOT_PERMISSION = "remove.toot";

    private Map<String, List<String>> permissionsMap;

    @Autowired
    StormpathSDKService stormpathSDKService;

    @PostConstruct
    void init() {
        permissionsMap = new HashMap<String, List<String>>();
        List<String> groups = new ArrayList<String>();

        groups.add(stormpathSDKService.getAdministratorGroupURL());
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

                outter:
                for (String group : permissionGroupURLs) {

                    if (groupMap.containsKey(group)) {

                        isGroupAllowed = true;
                        break;
                    } else {

                        for (String key : groupMap.keySet()) {

                            if (key.contains(group)) {
                                isGroupAllowed = true;
                                break outter;
                            }
                        }
                    }
                }
            }
        }

        return isGroupAllowed;
    }
}
