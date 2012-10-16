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
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.group.Group;
import com.stormpath.tooter.model.User;
import com.stormpath.tooter.model.dao.CustomerDao;
import com.stormpath.tooter.model.sdk.StormpathService;
import com.stormpath.tooter.validator.SignUpValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Elder Crisostomo
 */
@Controller
@RequestMapping("/signUp")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SignUpController {

    @Autowired
    CustomerDao customerDao;

    SignUpValidator singUpValidator;

    @Autowired
    StormpathService stormpath;


    public SignUpController() {
    }

    @Autowired
    public SignUpController(SignUpValidator signUpValidator) {
        this.singUpValidator = signUpValidator;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("customer") User customer, ModelMap model, BindingResult result, SessionStatus status) {

        singUpValidator.validate(customer, result);

        Map<String, String> groupMap = null;

        try {

            if (result.hasErrors()) {

                setGroupsToModel(groupMap, model);

                return "signUp";
            }

            String userName = customer.getFirstName().toLowerCase() + customer.getLastName().toLowerCase();

            // For account creation, we should get an instance of Account from the DataStore,
            // set the account properties and create it in the proper directory.
            Account account = stormpath.getDataStore().instantiate(Account.class);
            account.setEmail(customer.getEmail());
            account.setGivenName(customer.getFirstName());
            account.setSurname(customer.getLastName());
            account.setPassword(customer.getPassword());
            account.setUsername(userName);

            // Saving the account to the Directory where the Tooter application belongs.
            Directory directory = stormpath.getDirectory();
            directory.createAccount(account);

//            if (!User.NO_GROUP.equals(customer.getGroup())) {
//                account.addGroup(stormpath.getDataStore().getResource(customer.getGroup(), Group.class));
//            }

            customer.setUserName(userName);
            customerDao.saveCustomer(customer);

            status.setComplete();

        } catch (RuntimeException re) {

            setGroupsToModel(groupMap, model);

            result.addError(new ObjectError("password", re.getMessage()));
            re.printStackTrace();
            return "signUp";

        } catch (Exception e) {
            e.printStackTrace();
        }

        //form success
        return "redirect:/login/message?loginMsg=registered";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) {

        User cust = new User();

        Map<String, String> groupMap = null;

        setGroupsToModel(groupMap, model);

        model.addAttribute("customer", cust);

        //return form view
        return "signUp";
    }

    private void setGroupsToModel(Map<String, String> groupMap, ModelMap model) {

        groupMap = new HashMap<String, String>();

        for (Group group : stormpath.getDirectory().getGroups()) {
            groupMap.put(group.getHref(), group.getName());
        }

        model.addAttribute("groupMap", groupMap);

    }

}
