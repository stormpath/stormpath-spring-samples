package com.stormpath.tooter.controller;

import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.validator.SignUpValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/singUp")
public class SignUpController {

    SignUpValidator singUpValidator;

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
            return "singUp";
        } else {

            status.setComplete();

            //TODO: add redirect logic. SDK?


            //form success
            return "login";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) {

        Customer cust = new Customer();
        cust.setAccountType(Customer.BASIC_ACCOUNT);

        model.addAttribute("customer", cust);

        //return form view
        return "singUp";
    }

}
