package com.googleappengine.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CheckPasswordValidator.class)
public @interface CheckPassword {
    String message() default "Password must contains [a-zA-Z0-9_]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
