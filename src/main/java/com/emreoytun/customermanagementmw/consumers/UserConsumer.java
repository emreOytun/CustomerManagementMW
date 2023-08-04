package com.emreoytun.customermanagementmw.consumers;

import com.emreoytun.customermanagementdata.dto.user.UserDto;
import com.emreoytun.customermanagementmw.constants.CustomerManagementConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@DependsOn("customer-management-constants")
public class UserConsumer {

    private final RestTemplate restTemplate;
    private String baseUrl;

    public UserConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    private void init() {
        this.baseUrl = CustomerManagementConstants.CUSTOMER_MANAGEMENT_BACKEND_BASE_URL + "/users";
    }

    public ResponseEntity<UserDto> getUserByUsername(String username) {
        String requestUrl = baseUrl + "/{username}";
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("username", username);

        return restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                UserDto.class,
                uriVariables
        );
    }
}
