package com.emreoytun.customermanagementmw.handlers;

import com.emreoytun.customermanagementmw.business.exceptions.CustomerBusinessRulesException;
import com.emreoytun.customermanagementmw.dto.results.ErrorResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomerBusinessRulesException.class)
    public ResponseEntity<ErrorResult> handleCustomerBusinessRulesException(CustomerBusinessRulesException exception) {
        ErrorResult result = new ErrorResult(exception.getMessage());
        return new ResponseEntity<>(result, exception.getHttpStatus());
    }
}
