package com.emreoytun.customermanagementmw.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("customer-management-constants")
public class CustomerManagementConstants {
    public static String CUSTOMER_MANAGEMENT_BACKEND_BASE_URL;
    public static final int USER_EXPIRE_TIME_IN_MS = 5 * 60 * 1000;

    // Reading a constant from the properties file.
    @Value("${customer-management-backend.base-url}")
    public void setCustomerManagementBackendBaseUrl(String baseUrl) {
        CUSTOMER_MANAGEMENT_BACKEND_BASE_URL = baseUrl;
    }
}
