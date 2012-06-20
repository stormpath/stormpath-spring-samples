package com.stormpath.tooter.validator;

import com.stormpath.tooter.model.Customer;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/11/12
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProfileValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "profile.required.firstName", "Field first name is required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "profile.required.lastName", "Field last name is required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "profile.required.email", "Field email is required");


    }
}
