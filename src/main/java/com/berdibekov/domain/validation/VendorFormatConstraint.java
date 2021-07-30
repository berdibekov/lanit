package com.berdibekov.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VendorFormatValidator.class)
@Target( {ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface VendorFormatConstraint {
    String message() default "Invalid vendor format. should be [vendor]-[model]";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

