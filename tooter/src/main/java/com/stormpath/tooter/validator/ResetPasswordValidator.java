package com.stormpath.tooter.validator;

import com.stormpath.tooter.model.Customer;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/8/12
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResetPasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "reset.password.required.email", "Field email is required");
    }
}
