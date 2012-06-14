package com.stormpath.tooter.controller;

import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.model.dao.CustomerDao;
import com.stormpath.tooter.validator.SignUpValidator;
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

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/8/12
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/singUp")
@Transactional(propagation = Propagation.REQUIRED)
public class SignUpController {

    @Autowired
    CustomerDao customerDao;

    SignUpValidator singUpValidator;

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
            return "singUp";
        } else {

            status.setComplete();

            //TODO: add redirect logic. SDK?

            try {
                customer.setUserName(customer.getFirstName().toLowerCase() + customer.getLastName().toLowerCase());
                customerDao.saveOrUpdateCustomer(customer);
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
        return "singUp";
    }

}
