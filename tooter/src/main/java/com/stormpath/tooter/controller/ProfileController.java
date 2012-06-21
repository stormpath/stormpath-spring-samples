package com.stormpath.tooter.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.model.dao.CustomerDao;
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

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/11/12
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/profile")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProfileController {

    ProfileValidator profileValidator;

    @Autowired
    CustomerDao customerDao;

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

        model.addAttribute("customer", customer);

        //return form view
        return customer == null ? "redirect:/login/message?loginMsg=loginRequired" : "profile";
    }
}
