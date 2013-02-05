package com.googleappengine.util;

import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.Set;

public class ValidationUtils {
    /**
     * Get the first message in the queue and throw an exception.
     *
     * @param violations
     * @throws ValidationException
     */
    public static <T> void checkConstraintViolations(Set<ConstraintViolation<T>> violations)
            throws ValidationException {
        if (CollectionUtils.isNotEmpty(violations)) {
            // get the message
            String errorMessage = violations.iterator().next().getMessage();
            throw new ValidationException(errorMessage);
        }
    }
}
