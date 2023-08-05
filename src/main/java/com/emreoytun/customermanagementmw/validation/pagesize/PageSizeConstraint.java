package com.emreoytun.customermanagementmw.validation.pagesize;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PageSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageSizeConstraint {
    String message() default "Page size should not be smaller than 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
