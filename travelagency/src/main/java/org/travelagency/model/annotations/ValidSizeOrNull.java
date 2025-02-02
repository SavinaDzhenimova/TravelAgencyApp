package org.travelagency.model.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.travelagency.validation.SizeOrNullValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SizeOrNullValidator.class)
public @interface ValidSizeOrNull {
    String message() default "Полето трябва да бъде между 10 и 80 символа, или да бъде null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}