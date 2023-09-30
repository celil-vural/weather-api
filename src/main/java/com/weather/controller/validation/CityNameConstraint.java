package com.weather.controller.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {CityNameValidator.class})
@Target({METHOD, FIELD,PARAMETER})
@Retention(RUNTIME)
public @interface CityNameConstraint {
    String message() default "Invalid City";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
