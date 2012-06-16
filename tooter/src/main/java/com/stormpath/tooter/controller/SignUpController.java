package com.stormpath.tooter.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.Directory;
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
            //if validator failed
            //TODO: add SDK user validation
            return "signUp";
        } else {

            status.setComplete();

            //TODO: add redirect logic. SDK?

            try {

                String userName = customer.getFirstName().toLowerCase() + customer.getLastName().toLowerCase();

                Account account = stormpathSDKService.getDataStore().instantiate(Account.class);

                account.setEmail(customer.getEmail());
                account.setGivenName(customer.getFirstName());
                account.setSurname(customer.getLastName());
                account.setPassword(customer.getPassword());
                account.setUsername(userName);

                Directory directory = stormpathSDKService.getDirectory();
                directory.createAccount(account);

                customer.setUserName(userName);
                customerDao.saveCustomer(customer);
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
        cust.setAccountType(Customer.BASIC_ACCOUNT);

        model.addAttribute("customer", cust);

        //return form view
        return "signUp";
    }

}
