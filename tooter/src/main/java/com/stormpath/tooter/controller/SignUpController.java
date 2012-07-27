package com.stormpath.tooter.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.group.Group;
import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.model.dao.CustomerDao;
import com.stormpath.tooter.model.sdk.StormpathSDKService;
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
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/8/12
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/signUp")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class SignUpController {

    @Autowired
    CustomerDao customerDao;

    SignUpValidator singUpValidator;

    @Autowired
    StormpathSDKService stormpathSDKService;


    public SignUpController() {
    }

    @Autowired
    public SignUpController(SignUpValidator signUpValidator) {
        this.singUpValidator = signUpValidator;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("customer") Customer customer, BindingResult result, SessionStatus status) {

        singUpValidator.validate(customer, result);

        if (result.hasErrors()) {

            return "signUp";
        } else {

            try {

                String userName = customer.getFirstName().toLowerCase() + customer.getLastName().toLowerCase();

                // For account creation, we should get an instance of Account from the DataStore,
                // set the account properties and create it in the proper directory.
                Account account = stormpathSDKService.getDataStore().instantiate(Account.class);
                account.setEmail(customer.getEmail());
                account.setGivenName(customer.getFirstName());
                account.setSurname(customer.getLastName());
                account.setPassword(customer.getPassword());
                account.setUsername(userName);
                account.addGroup(stormpathSDKService.getDataStore().getResource(customer.getAccountType(), Group.class));

                // Saving the account to the Directory where the Tooter application belongs.
                Directory directory = stormpathSDKService.getDirectory();
                directory.createAccount(account);

                customer.setUserName(userName);
                customerDao.saveCustomer(customer);

                status.setComplete();

            } catch (RuntimeException re) {
                result.addError(new ObjectError("password", re.getMessage()));
                re.printStackTrace();
                return "signUp";

            } catch (Exception e) {
                e.printStackTrace();
            }

            //form success
            return "redirect:/login/message?loginMsg=registered";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) {

        Customer cust = new Customer();

        Map<String, String> groupMap = new HashMap<String, String>();

        for (Group group : stormpathSDKService.getDirectory().getGroups()) {
            groupMap.put(group.getHref(), group.getName());
        }

        model.addAttribute("groupMap", groupMap);

        model.addAttribute("customer", cust);

        //return form view
        return "signUp";
    }

}
