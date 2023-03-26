package com.emreoytun.customermanagementmw.business.exceptions;

import org.springframework.http.HttpStatus;

// Custom exception for customer business operations.
public class DtoTransformationException extends RuntimeException {

    public DtoTransformationException(String message) {
        super(message);
    }

    public DtoTransformationException(String message, HttpStatus httpStatus) {
        super(message);
    }

}
