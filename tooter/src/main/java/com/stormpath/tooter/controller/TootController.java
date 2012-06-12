package com.stormpath.tooter.controller;

import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.model.Toot;
import com.stormpath.tooter.validator.TootValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

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
    public String processSubmit(@ModelAttribute("customer") Customer cust, BindingResult result, SessionStatus status) {

        tootValidator.validate(cust, result);

        if (result.hasErrors()) {
            //if validator failed
            //TODO: add SDK user validation
            return "tooter";
        } else {

            status.setComplete();

            //TODO: add Reset Password redirect logic. SDK?

            cust.setAccountType("Premium");
            cust.setFirstName("Some");
            cust.setLastName("Body");
            cust.setUserName("somebody");

            List<Toot> tootList = new ArrayList<Toot>();

            Toot toot = new Toot();
            toot.setTootId(1);
            toot.setCustomer(cust);
            toot.setTootMessage("This is one toooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooot!!!!!!!!!!!!!!");
            tootList.add(toot);

            toot = new Toot();
            toot.setTootId(2);
            toot.setCustomer(cust);
            toot.setTootMessage("This is anooooooooooooooother tooooooooooooooooooooooooooooooooooot!!!!!!!!!");
            tootList.add(toot);

            cust.setTootList(tootList);

            //form success
            return "tooter";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) {

        Customer cust = new Customer();

        cust.setAccountType("Premium");
        cust.setFirstName("Some");
        cust.setLastName("Body");
        cust.setUserName("somebody");

        List<Toot> tootList = new ArrayList<Toot>();

        Toot toot = new Toot();
        toot.setTootId(1);
        toot.setCustomer(cust);
        toot.setTootMessage("This is one toooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooot!!!!!!!!!!!!!!");
        tootList.add(toot);

        toot = new Toot();
        toot.setTootId(2);
        toot.setCustomer(cust);
        toot.setTootMessage("This is anooooooooooooooother tooooooooooooooooooooooooooooooooooot!!!!!!!!!");
        tootList.add(toot);

        cust.setTootList(tootList);

        model.addAttribute("tootList", tootList);

        model.addAttribute("customer", cust);

        //return form view
        return "tooter";
    }
}
