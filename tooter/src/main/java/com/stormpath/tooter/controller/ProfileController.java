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
import com.stormpath.sdk.group.Group;
import com.stormpath.sdk.group.GroupMembership;
import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.model.dao.CustomerDao;
import com.stormpath.tooter.model.sdk.StormpathSDKService;
import com.stormpath.tooter.validator.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Elder Crisostomo
 */
@Controller
@RequestMapping("/profile")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProfileController {

    ProfileValidator profileValidator;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    StormpathSDKService stormpathSDKService;

    public ProfileController() {
    }

    @Autowired
    public ProfileController(ProfileValidator profileValidator) {
        this.profileValidator = profileValidator;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("customer") Customer customer,
                                BindingResult result,
                                SessionStatus status,
                                HttpSession session,
                                ModelMap model) {

        profileValidator.validate(customer, result);

        if (!result.hasErrors()) {

            try {

                // For account update, we just retrieve the current account and
                // call Account.save() after setting the modified properties.
                // An account can also be retrieved from the DataStore,
                // like the way we do it to get an Application or Directory object,
                // if the account's Rest URL is known to the application.
                Account account = (Account) session.getAttribute("stormpathAccount");
                account.setSurname(customer.getLastName());
                account.setEmail(customer.getEmail());
                account.setGivenName(customer.getFirstName());

                if (account.getGroupMemberships().iterator().hasNext()) {

                    GroupMembership groupMembership = account.getGroupMemberships().iterator().next();
                    if (!groupMembership.getGroup().getHref().equals(customer.getAccountType())) {

                        groupMembership.delete();
                        account.addGroup(stormpathSDKService.getDataStore().getResource(customer.getAccountType(), Group.class));
                    }

                } else {

                    account.addGroup(stormpathSDKService.getDataStore().getResource(customer.getAccountType(), Group.class));
                }


                account.save();

                Customer sessionCustomer = (Customer) session.getAttribute("sessionCustomer");
                sessionCustomer.setAccountType(customer.getAccountType());
                sessionCustomer.setEmail(customer.getEmail());
                sessionCustomer.setFirstName(customer.getFirstName());
                sessionCustomer.setLastName(customer.getLastName());

                customer.setUserName(sessionCustomer.getUserName());
                customer.setTootList(sessionCustomer.getTootList());

                model.addAttribute("messageKey", "updated");
                model.addAttribute("customer", customer);


            } catch (Exception e) {
                e.printStackTrace();
            }

            status.setComplete();


        }
        return "profile";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model, HttpSession session) {


        Customer customer = (Customer) session.getAttribute("sessionCustomer");

        Map<String, String> groupMap = new HashMap<String, String>();

        for (Group group : stormpathSDKService.getDirectory().getGroups()) {
            groupMap.put(group.getHref(), group.getName());
        }

        session.setAttribute("groupMap", groupMap);

        model.addAttribute("customer", customer);

        //return form view
        return customer == null ? "redirect:/login/message?loginMsg=loginRequired" : "profile";
    }
}
