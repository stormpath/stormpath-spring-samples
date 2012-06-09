package com.stormpath.tooter.controller;

import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.validator.TootValidator;
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
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/tooter")
public class TootController {

    TootValidator tootValidator;

    @Autowired
    public TootController(TootValidator tootValidator) {
        this.tootValidator = tootValidator;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("customer") Customer customer, BindingResult result, SessionStatus status) {

        tootValidator.validate(customer, result);

        if (result.hasErrors()) {
            //if validator failed
            //TODO: add SDK user validation
            return "tooter";
        } else {

            status.setComplete();

            //TODO: add Reset Password redirect logic. SDK?


            //form success
            return "tooter";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) {

        Customer cust = new Customer();

        model.addAttribute("customer", cust);

        //return form view
        return "tooter";
    }
}
