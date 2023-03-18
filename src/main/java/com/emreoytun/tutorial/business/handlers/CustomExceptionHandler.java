package com.emreoytun.tutorial.business.handlers;

import com.emreoytun.tutorial.business.exceptions.CustomerBusinessRulesException;
import com.emreoytun.tutorial.dto.results.ErrorResult;
import org.springframework.http.HttpStatus;
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
