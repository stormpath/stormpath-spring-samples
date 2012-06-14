package com.stormpath.tooter.validator;

import com.stormpath.tooter.model.Toot;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: ecrisostomo
 * Date: 6/8/12
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class TootValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Toot.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tootMessage", "tooter.required", "Field toot is required");

        Toot toot = (Toot) o;

        if (toot.getTootMessage() != null && toot.getTootMessage().length() > 160) {
            errors.rejectValue("tootMessage", "tooter.too.many.chars");
        }
    }
}
