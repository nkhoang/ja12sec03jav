package com.googleappengine.validator;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPasswordValidator implements ConstraintValidator<CheckPassword, String> {

    @Override
    public void initialize(CheckPassword constraintAnnotation) {
        // do nothing.
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isNotBlank(value)) {
            Pattern pattern = Pattern.compile("/w+/");
            Matcher matcher = pattern.matcher(value);
            return matcher.find();
        }
        return false;
    }
}
