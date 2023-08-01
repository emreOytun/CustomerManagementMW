package com.emreoytun.customermanagementmw.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomerManagementConstants {
    public static String CUSTOMER_MANAGEMENT_BACKEND_BASE_URL;

    // Reading a constant from the properties file.
    @Value("${customer-management-backend.base-url}")
    public void setCustomerManagementBackendBaseUrl(String baseUrl) {
        CUSTOMER_MANAGEMENT_BACKEND_BASE_URL = baseUrl;
    }
}
