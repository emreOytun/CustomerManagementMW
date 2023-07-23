package com.emreoytun.customermanagementmw.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class HttpStatusChecker {
    public static boolean checkIfHttpOk(HttpStatusCode httpStatus) {
        return httpStatus == HttpStatus.OK ? true : false;
    }
}
