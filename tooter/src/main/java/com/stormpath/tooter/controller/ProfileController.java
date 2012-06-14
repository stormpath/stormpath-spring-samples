package com.stormpath.tooter.controller;

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
@Transactional(propagation = Propagation.REQUIRED)
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
                                HttpSession session) {

        profileValidator.validate(customer, result);

        if (result.hasErrors()) {
            //if validator failed
            //TODO: add SDK user validation
        } else {

            status.setComplete();

            try {
                Customer cust = customerDao.saveOrUpdateCustomer(customer);
                session.setAttribute("sessionCustomer", cust);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "profile";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model, HttpSession session) {


        Customer cust = (Customer) session.getAttribute("sessionCustomer");
        ;

        try {
            cust = customerDao.getCustomerByUserName(cust.getUserName());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        model.addAttribute("customer", cust);

        //return form view
        return "profile";
    }
}
