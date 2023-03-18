package com.emreoytun.customermanagementmw.business.exceptions;

import org.springframework.http.HttpStatus;

// Custom exception for customer business operations.
public class CustomerBusinessRulesException extends RuntimeException {

    private HttpStatus httpStatus;

    public CustomerBusinessRulesException(String message) {
        super(message);
        httpStatus = HttpStatus.BAD_REQUEST;
    }

    public CustomerBusinessRulesException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
