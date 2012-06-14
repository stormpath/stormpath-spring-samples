package com.stormpath.tooter.controller;

import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.validator.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
    public LoginController(LoginValidator loginValidator) {
        this.loginValidator = loginValidator;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("customer") Customer customer,
                                BindingResult result,
                                SessionStatus status,
                                HttpSession session) {

        loginValidator.validate(customer, result);

        if (result.hasErrors()) {
            //if validator failed
            //TODO: add SDK user validation
            return "login";
        } else {

            status.setComplete();

            //TODO: add Reset Password redirect logic. SDK?

            session.setAttribute("accountId", customer.getUserName());
            //form success
            return "redirect:/tooter?accountId=" + customer.getUserName();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(@ModelAttribute("customer") Customer customer, BindingResult result, ModelMap model) {

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

}
