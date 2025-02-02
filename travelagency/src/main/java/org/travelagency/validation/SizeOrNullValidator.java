package org.travelagency.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.travelagency.model.annotations.ValidSizeOrNull;

public class SizeOrNullValidator implements ConstraintValidator<ValidSizeOrNull, String> {

    @Override
    public void initialize(ValidSizeOrNull constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.isEmpty()) {
            return true;
        }

        return value.length() >= 10 && value.length() <= 80;
    }
}