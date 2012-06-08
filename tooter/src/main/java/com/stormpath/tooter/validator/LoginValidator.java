package com.stormpath.tooter.validator;

import com.stormpath.tooter.model.Customer;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/7/12
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "login.required.userName", "Field name is required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "login.required.password", "Field password is required");
    }
}
