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
package com.stormpath.tooter.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.group.GroupMembership;
import com.stormpath.sdk.group.GroupMembershipList;
import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.model.Toot;
import com.stormpath.tooter.model.dao.CustomerDao;
import com.stormpath.tooter.model.dao.TootDao;
import com.stormpath.tooter.model.sdk.StormpathService;
import com.stormpath.tooter.util.PermissionUtil;
import com.stormpath.tooter.validator.TootValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Elder Crisostomo
 */
@Controller
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TootController {

    TootValidator tootValidator;

    @Autowired
    TootDao tootDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    StormpathService stormpath;

    @Autowired
    PermissionUtil permissionUtil;

    public TootController() {
    }

    @Autowired
    public TootController(TootValidator tootValidator) {
        this.tootValidator = tootValidator;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tooter")
    public String processSubmit(@ModelAttribute("toot") Toot toot,
                                BindingResult result,
                                SessionStatus status,
                                HttpSession session) {

        tootValidator.validate(toot, result);

        Customer sessionCustomer = (Customer) session.getAttribute("sessionCustomer");

        if (!result.hasErrors()) {

            Customer persistCustomer = new Customer(sessionCustomer);

            List<Toot> tootList;
            Toot persistToot = new Toot();
            persistToot.setTootMessage(toot.getTootMessage());
            persistToot.setCustomer(persistCustomer);

            try {

                tootDao.saveToot(persistToot);
                toot.setTootId(persistToot.getTootId());
                tootList = tootDao.getTootsByUserId(persistCustomer.getId());

                for (Toot itemToot : tootList) {
                    itemToot.setCustomer(sessionCustomer);
                }

                Collections.sort(tootList);
                sessionCustomer.setTootList(tootList);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        toot.setTootMessage("");
        toot.setCustomer(sessionCustomer);

        status.setComplete();


        //form success
        return "tooter";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tooter")
    public String initForm(@RequestParam("accountId") String userName,
                           ModelMap model,
                           @ModelAttribute("toot") Toot toot,
                           BindingResult result,
                           HttpSession session) {

        List<Toot> tootList;

        Toot tooot = new Toot();

        try {

            Customer customer = (Customer) session.getAttribute("sessionCustomer");

            if (customer == null || customer.getId() == null) {

                Customer dbCustomer = customerDao.getCustomerByUserName(userName);
                customer = new Customer(dbCustomer);
            } else {

                session.setAttribute("permissionUtil", permissionUtil);

                session.setAttribute("removeTootPermission", PermissionUtil.REMOVE_TOOT_PERMISSION);

                Account account = (Account) session.getAttribute("stormpathAccount");

                Map<String, String> groupURLs = new HashMap<String, String>();
                groupURLs.put(Customer.BASIC_ACCOUNT_TYPE, Customer.BASIC_ACCOUNT_TYPE);

                GroupMembershipList groupMembershipList = account.getGroupMemberships();

                for (GroupMembership groupMembership : groupMembershipList) {
                    groupURLs.put(groupMembership.getGroup().getHref(), groupMembership.getGroup().getName());
                }

                session.setAttribute("accountGroups", groupURLs);
            }

            tootList = tootDao.getTootsByUserId(customer.getId());

            for (Toot itemToot : tootList) {
                itemToot.setCustomer(customer);
            }

            Collections.sort(tootList);
            customer.setTootList(tootList);
            tooot.setCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("toot", tooot);

        return "tooter";
    }

    @RequestMapping("/tooter/remove")
    public String removeToot(@RequestParam("accountId") String userName,
                             @RequestParam("removeTootId") String removeTootId,
                             ModelMap model,
                             @ModelAttribute("Toot") Toot toot,
                             HttpSession session) {


        try {
            tootDao.removeTootById(Integer.valueOf(removeTootId));
            userName = userName == null || userName.isEmpty() ?
                    ((Customer) session.getAttribute("sessionCustomer")).getUserName() :
                    userName;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/tooter?accountId=" + userName;
    }
}
