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
import com.stormpath.tooter.model.User;
import com.stormpath.tooter.model.dao.CustomerDao;
import com.stormpath.tooter.model.sdk.StormpathService;
import com.stormpath.tooter.validator.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    StormpathService stormpath;

    @Value("${stormpath.sdk.administrator.rest.url}")
    private String administratorGroupURL;

    @Value("${stormpath.sdk.premium.rest.url}")
    private String premiumGroupURL;

    public ProfileController() {
    }

    @Autowired
    public ProfileController(ProfileValidator profileValidator) {
        this.profileValidator = profileValidator;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("user") User user,
                                BindingResult result,
                                SessionStatus status,
                                HttpSession session,
                                ModelMap model) {

        profileValidator.validate(user, result);

        if (!result.hasErrors()) {

            try {

                // For account update, we just retrieve the current account and
                // call Account.save() after setting the modified properties.
                // An account can also be retrieved from the DataStore,
                // like the way we do it to get an Application or Directory object,
                // if the account's Rest URL is known to the application.

                User sessionUser = (User) session.getAttribute("sessionUser");
                Account account = sessionUser.getAccount();
                account.setGivenName(user.getFirstName());
                account.setSurname(user.getFirstName());
                account.setEmail(user.getEmail());

                if (account.getGroupMemberships().iterator().hasNext()) {
                    GroupMembership groupMembership = account.getGroupMemberships().iterator().next();
                    if (!groupMembership.getGroup().getHref().equals(user.getGroupUrl())) {
                        groupMembership.delete();
                    }
                }

                if (user.getGroupUrl() != null && !user.getGroupUrl().isEmpty()) {
                    account.addGroup(stormpath.getDataStore().getResource(user.getGroupUrl(), Group.class));
                }

                account.save();

                user.setAccount(account);
                user.setUserName(sessionUser.getUserName());
                user.setTootList(sessionUser.getTootList());

                model.addAttribute("messageKey", "updated");
                model.addAttribute("ADMINISTRATOR_URL", administratorGroupURL);
                model.addAttribute("PREMIUM_URL", premiumGroupURL);
                model.addAttribute("user", user);
            } catch (Exception e) {
                e.printStackTrace();
            }

            status.setComplete();
        }
        return "profile";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model, HttpSession session) {
        User user = (User) session.getAttribute("sessionUser");

        model.addAttribute("user", user);
        model.addAttribute("ADMINISTRATOR_URL", administratorGroupURL);
        model.addAttribute("PREMIUM_URL", premiumGroupURL);

        return user == null ? "redirect:/login" : "profile";
    }
}