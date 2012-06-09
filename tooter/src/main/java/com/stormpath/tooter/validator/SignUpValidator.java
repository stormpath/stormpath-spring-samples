package com.stormpath.tooter.validator;

import com.stormpath.tooter.model.Customer;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/8/12
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class SignUpValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "signUp.required.firstName", "Field first name is required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "singUp.required.password", "Field password is required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "signUp.required.lastName", "Field last name is required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "signUp.required.email", "Field email is required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmEmail", "signUp.required.confirm.password", "Field confirm email is required");

        Customer customer = (Customer) o;

        if (!customer.getPassword().equals(customer.getConfirmPassword())) {
            errors.rejectValue("password", "password.not.match");
        }
    }
}