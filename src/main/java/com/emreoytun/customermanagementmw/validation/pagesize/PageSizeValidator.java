package com.emreoytun.customermanagementmw.validation.pagesize;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PageSizeValidator implements ConstraintValidator<PageSizeConstraint, Integer> {

    @Override
    public void initialize(PageSizeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer pageSize, ConstraintValidatorContext context) {
        if (pageSize < 0) return false;
        return true;
    }
}
