package com.stormpath.tooter.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.model.dao.CustomerDao;
import com.stormpath.tooter.model.sdk.StormpathSDKService;
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
 * @since 0.1
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private LoginValidator loginValidator;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    StormpathSDKService stormpathSDKService;

    @Autowired
    public LoginController(LoginValidator loginValidator) {
        this.loginValidator = loginValidator;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("customer") Customer customer,
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
                Account account = stormpathSDKService.getApplication().
                        authenticate(
                                new UsernamePasswordRequest(
                                        customer.getUserName(),
                                        customer.getPassword()));

                Customer accCustomer = new Customer(account);


                // If the customer queried from the database does not exist
                // we create it in the application's internal database,
                // so we don't have to go through the process of signing a user up
                // that has already been authenticated in the previous call to the Stormpath API.
                // This is because the application uses an in-memory database (HSQLDB)
                // that only persists while the application is up.
                Customer dbCustomer = customerDao.getCustomerByUserName(customer.getUserName());
                if (dbCustomer == null) {

                    customerDao.saveCustomer(accCustomer);
                }

                // We save the Stormpath account in the session for later use.
                session.setAttribute("stormpathAccount", account);

                if (dbCustomer != null) {
                    accCustomer.setId(dbCustomer.getId());
                }

                session.setAttribute("sessionCustomer", accCustomer);

                returnStr = "redirect:/tooter?accountId=" + customer.getUserName();

                status.setComplete();
            } catch (RuntimeException re) {

                result.addError(new ObjectError("userName", re.getMessage()));
                re.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return returnStr;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(@ModelAttribute("customer") Customer customer, BindingResult result, ModelMap model) {

        try {

            stormpathSDKService.getClient().getCurrentTenant();

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
                              @ModelAttribute("customer") Customer customer,
                              BindingResult result,
                              ModelMap model) {

        model.addAttribute("customer", customer);
        model.addAttribute("messageKey", messageKey);

        //return form view
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/emailVerificationTokens")
    public String accountVerification(@RequestParam("sptoken") String token,
                                      @ModelAttribute("customer") Customer customer,
                                      BindingResult result) {

        String returnStr = "login";

        try {

            stormpathSDKService.getClient().getCurrentTenant().verifyAccountEmail(token);
            returnStr = "redirect:/login/message?loginMsg=accVerified";

        } catch (RuntimeException re) {

            result.addError(new ObjectError("userName", re.getMessage()));
            re.printStackTrace();
        }

        return returnStr;

    }


}
