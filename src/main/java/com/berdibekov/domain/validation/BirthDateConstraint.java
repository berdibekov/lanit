package com.berdibekov.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDateConstraint {
    String message() default "Invalid date format. Should be [dd.MM.yyyy]";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String format();
}
