package com.stormpath.tooter.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.tooter.model.Customer;
import com.stormpath.tooter.model.dao.CustomerDao;
import com.stormpath.tooter.model.sdk.StormpathSDKService;
import com.stormpath.tooter.validator.ChangePasswordValidator;
import com.stormpath.tooter.validator.ResetPasswordValidator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/8/12
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class PasswordController {

    ChangePasswordValidator changePasswordValidator;

    ResetPasswordValidator resetPasswordValidator;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    StormpathSDKService stormpathSDKService;

    public PasswordController() {
    }

    @Autowired
    public PasswordController(ChangePasswordValidator changePasswordValidator, ResetPasswordValidator resetPasswordValidator) {
        this.changePasswordValidator = changePasswordValidator;
        this.resetPasswordValidator = resetPasswordValidator;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resetPassword")
    public String processResetPassword(@ModelAttribute("customer") Customer customer, BindingResult result, SessionStatus status) {

        resetPasswordValidator.validate(customer, result);

        if (result.hasErrors()) {

            return "resetPassword";
        } else {

            try {

                stormpathSDKService.getApplication().createPasswordResetToken(customer.getEmail());
                status.setComplete();

            } catch (RuntimeException re) {
                result.addError(new ObjectError("email", re.getMessage()));
                re.printStackTrace();
                return "resetPassword";
            }


            //form success
            return "resetPasswordMsg";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changePassword")
    public String processChangePassword(@ModelAttribute("customer") Customer customer,
                                        BindingResult result,
                                        SessionStatus status,
                                        HttpSession session) {

        changePasswordValidator.validate(customer, result);

        if (result.hasErrors()) {
            return "changePassword";
        } else {

            try {

                Account account = (Account) session.getAttribute("stormpathAccount");
                account.setPassword(customer.getPassword());

                account.save();
                session.removeAttribute("stormpathAccount");

                status.setComplete();

            } catch (RuntimeException re) {
                result.addError(new ObjectError("password", re.getMessage()));
                re.printStackTrace();
                return "changePassword";
            }

            //form success
            return "redirect:/login/message?loginMsg=passChanged";
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
    public String initChangePassword(@RequestParam("sptoken") String passToken,
                                     ModelMap model,
                                     @ModelAttribute("customer") Customer cust,
                                     BindingResult result,
                                     HttpSession session) {

        if (passToken.isEmpty()) {
            result.addError(new ObjectError("password", "The passToken parameter can't be empty"));
        }

        try {

            Account account = stormpathSDKService.getApplication().verifyPasswordResetToken(passToken).getAccount();

            session.setAttribute("stormpathAccount", account);

        } catch (RuntimeException re) {
            result.addError(new ObjectError("password", re.getMessage()));
            re.printStackTrace();
        }

        //return form view
        return "changePassword";
    }
}
