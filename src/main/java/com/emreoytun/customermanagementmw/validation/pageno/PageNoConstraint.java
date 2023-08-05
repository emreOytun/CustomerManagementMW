package com.emreoytun.customermanagementmw.validation.pageno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PageNoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageNoConstraint {
    String message() default "Page no should not be smaller than 1";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
