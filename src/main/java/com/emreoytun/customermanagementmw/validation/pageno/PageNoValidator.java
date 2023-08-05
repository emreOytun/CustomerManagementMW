package com.emreoytun.customermanagementmw.validation.pageno;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PageNoValidator implements ConstraintValidator<PageNoConstraint, Integer> {
    @Override
    public void initialize(PageNoConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer pageNo, ConstraintValidatorContext context) {
        if (pageNo <= 0) return false;
        return true;
    }
}
