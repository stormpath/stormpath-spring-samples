package com.stormpath.tooter.validator;

import com.stormpath.tooter.model.Customer;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/8/12
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChangePasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "change.password.required.password", "Field password is required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "change.password.required.confirm.password", "Field confirm password is required");

        Customer customer = (Customer) o;

        if (!customer.getPassword().equals(customer.getConfirmPassword())) {
            errors.rejectValue("password", "password.not.match");
        }

    }
}
