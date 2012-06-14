package com.stormpath.tooter.controller;

import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.model.Toot;
import com.stormpath.tooter.model.dao.CustomerDao;
import com.stormpath.tooter.model.dao.TootDao;
import com.stormpath.tooter.validator.TootValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
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
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TootController {

    TootValidator tootValidator;

    @Autowired
    TootDao tootDao;

    @Autowired
    CustomerDao customerDao;

    public TootController() {
    }

    @Autowired
    public TootController(TootValidator tootValidator) {
        this.tootValidator = tootValidator;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tooter")
    public String processSubmit(@ModelAttribute("toot") Toot toot,
                                BindingResult result,
                                SessionStatus status,
                                HttpSession session) {

        tootValidator.validate(toot, result);

        if (result.hasErrors()) {
            //if validator failed
            //TODO: add SDK user validation
            return "tooter";
        } else {

            Customer sessionCustomer = (Customer) session.getAttribute("sessionCustomer");
            Customer persistCustomer = new Customer();
            persistCustomer.setId(sessionCustomer.getId());

            //TODO: add Reset Password redirect logic. SDK?


            List<Toot> tootList = new ArrayList<Toot>();
            Toot persistToot = new Toot();
            persistToot.setTootMessage(toot.getTootMessage());
            persistToot.setCustomer(persistCustomer);

            try {

                tootDao.saveToot(persistToot);
                tootList = tootDao.getTootsByUserId(persistCustomer.getId());
                sessionCustomer.setTootList(tootList);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            toot.setTootMessage("");
            toot.setCustomer(sessionCustomer);

            status.setComplete();


            //form success
            return "tooter";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tooter")
    public String initForm(@RequestParam("accountId") String userName,
                           ModelMap model,
                           @ModelAttribute("toot") Toot toot,
                           BindingResult result,
                           HttpSession session) {

        List<Toot> tootList = new ArrayList<Toot>();

        Toot tooot = new Toot();

        try {
            Object accountIdAtt = session.getAttribute("accountId");
            String accountId = accountIdAtt == null ? userName : (String) accountIdAtt;
            session.removeAttribute("accountId");

            Object sessionCustObj = session.getAttribute("sessionCustomer");
            Integer custId = sessionCustObj == null ? 0 : ((Customer) sessionCustObj).getId();

            Customer customer = customerDao.getCustomerByUserName(accountId);
            session.setAttribute("sessionCustomer", customer);

            tootList = tootDao.getTootsByUserId(custId > 0 ? custId : customer.getId());
            customer.setTootList(tootList);
            tooot.setCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        model.addAttribute("toot", tooot);

        //return form view
        return "tooter";
    }

    @RequestMapping("/tooter/remove")
    public String removeToot(@RequestParam("accountId") String userName,
                             @RequestParam("removeTootId") String removeTootId,
                             ModelMap model,
                             @ModelAttribute("Toot") Toot toot,
                             HttpSession session) {


        try {
            tootDao.removeTootById(Integer.valueOf(removeTootId));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        //return form view
        return "redirect:/tooter?accountId=" + userName;
    }
}
