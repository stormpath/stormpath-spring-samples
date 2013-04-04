/*
 * Copyright 2013 Stormpath, Inc. and contributors.
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
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.resource.ResourceException;
import com.stormpath.tooter.model.User;
import com.stormpath.tooter.model.dao.CustomerDao;
import com.stormpath.tooter.model.sdk.StormpathService;
import com.stormpath.tooter.util.PermissionUtil;
import com.stormpath.tooter.validator.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

/**
 * @author Elder Crisostomo
 * @since 0.1
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private LoginValidator loginValidator;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    StormpathService stormpath;

    @Autowired
    PermissionUtil permissionUtil;

    @Autowired
    public LoginController(LoginValidator loginValidator) {
        this.loginValidator = loginValidator;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("customer") User customer,
                                BindingResult result,
                                SessionStatus status,
                                HttpSession session) {

        loginValidator.validate(customer, result);

        String returnStr = "login";

        if (!result.hasErrors()) {

            try {

                // For authentication the only thing we need to do is call Application.authenticate(),
                // instantiating the proper AuthenticationRequest (UsernamePasswordRequest in this case),
                // providing the account's credentials.
                AuthenticationRequest request = new UsernamePasswordRequest(customer.getUserName(), customer.getPassword());
                AuthenticationResult authcResult = stormpath.getApplication().authenticateAccount(request);

                Account account = authcResult.getAccount();

                User user = new User(account);

                // If the customer queried from the database does not exist
                // we create it in the application's internal database,
                // so we don't have to go through the process of signing a user up
                // that has already been authenticated in the previous call to the Stormpath API.
                // This is because the application uses an in-memory database (HSQLDB)
                // that only persists while the application is up.
                User dbCustomer = customerDao.getCustomerByUserName(customer.getUserName());
                if (dbCustomer == null) {
                    customerDao.saveCustomer(user);
                }

                if (dbCustomer != null) {
                    user.setId(dbCustomer.getId());
                }

                session.setAttribute("sessionUser", user);
                session.setAttribute("permissionUtil", permissionUtil);

                returnStr = "redirect:/tooter";

                status.setComplete();
            } catch (ResourceException re) {
                result.addError(new ObjectError("userName", re.getMessage()));
                re.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return returnStr;
    }

    @RequestMapping(method = RequestMethod.POST, value = "message")
    public String processSubmitMsg(@ModelAttribute("customer") User customer,
                                   BindingResult result,
                                   SessionStatus status,
                                   HttpSession session) {
        return processSubmit(customer, result, status, session);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(@ModelAttribute("customer") User customer, BindingResult result, ModelMap model) {
        try {
            stormpath.getTenant();
        } catch (Throwable t) {
            result.addError(new ObjectError("userName",
                    "You have not finished configuring this sample application. " +
                            "Please follow the <a href=\"https://github.com/stormpath/stormpath-spring-samples/wiki/Tooter\">" +
                            "Set-up Instructions</a>"));
            t.printStackTrace();
        }

        model.addAttribute("customer", customer);

        //return form view
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/message")
    public String initMessage(@RequestParam("loginMsg") String messageKey,
                              @ModelAttribute("customer") User customer,
                              BindingResult result,
                              ModelMap model) {

        model.addAttribute("customer", customer);
        model.addAttribute("messageKey", messageKey);

        //return form view
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/emailVerificationTokens")
    public String accountVerification(@RequestParam("sptoken") String token,
                                      @ModelAttribute("customer") User customer,
                                      BindingResult result) {

        String returnStr = "login";

        try {

            stormpath.getTenant().verifyAccountEmail(token);
            returnStr = "redirect:/login/message?loginMsg=accVerified";

        } catch (RuntimeException re) {

            result.addError(new ObjectError("userName", re.getMessage()));
            re.printStackTrace();
        }

        return returnStr;

    }


}
