package com.stormpath.tooter.controller;

import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.validator.ChangePasswordValidator;
import com.stormpath.tooter.validator.ResetPasswordValidator;
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
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PasswordController {

    ChangePasswordValidator changePasswordValidator;

    ResetPasswordValidator resetPasswordValidator;

    @Autowired
    public PasswordController(ChangePasswordValidator changePasswordValidator, ResetPasswordValidator resetPasswordValidator) {
        this.changePasswordValidator = changePasswordValidator;
        this.resetPasswordValidator = resetPasswordValidator;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resetPassword")
    public String processResetPassword(@ModelAttribute("customer") Customer customer, BindingResult result, SessionStatus status) {

        resetPasswordValidator.validate(customer, result);

        if (result.hasErrors()) {
            //if validator failed
            //TODO: add SDK user validation
            return "resetPassword";
        } else {

            status.setComplete();

            //TODO: add redirect logic. SDK?


            //form success
            return "resetPasswordMsg";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changePassword")
    public String processChangePassword(@ModelAttribute("customer") Customer customer, BindingResult result, SessionStatus status) {

        changePasswordValidator.validate(customer, result);

        if (result.hasErrors()) {
            //if validator failed
            //TODO: add SDK user validation
            return "changePassword";
        } else {

            status.setComplete();

            //TODO: add redirect logic. SDK?


            //form success
            return "login";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/resetPassword")
    public String initResetPassword(ModelMap model) {

        Customer cust = new Customer();

        model.addAttribute("customer", cust);

        //return form view
        return "resetPassword";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/resetPasswordMsg")
    public String initResetPasswordMsg(ModelMap model) {

        Customer cust = new Customer();

        model.addAttribute("customer", cust);

        //return form view
        return "resetPasswordMsg";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/changePassword")
    public String initChangePassword(ModelMap model) {

        Customer cust = new Customer();

        model.addAttribute("customer", cust);

        //return form view
        return "changePassword";
    }
}
