package com.stormpath.tooter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/11/12
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {

    @RequestMapping(method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout";
    }
}
