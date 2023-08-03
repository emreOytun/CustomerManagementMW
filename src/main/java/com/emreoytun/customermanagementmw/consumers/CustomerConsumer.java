package com.emreoytun.customermanagementmw.consumers;

import com.emreoytun.customermanagementdata.dto.customer.CustomerWithPostsDto;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementdata.dto.customer.CustomerDto;
import com.emreoytun.customermanagementmw.constants.CustomerManagementConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerConsumer {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public CustomerConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        baseUrl = CustomerManagementConstants.CUSTOMER_MANAGEMENT_BACKEND_BASE_URL + "/v1/customers";
    }

    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        ParameterizedTypeReference<List<CustomerDto>> responseType = new ParameterizedTypeReference<>() {};

        String requestUrl = baseUrl;

        ResponseEntity<List<CustomerDto>> responseEntity = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                responseType);
        return responseEntity;
    }

    public ResponseEntity<List<CustomerDto>> getAllCustomersByPage(int pageSize, int pageNo) {
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("pageSize", String.valueOf(pageSize));
        uriVariables.put("pageNo", String.valueOf(pageNo));

        String requestUrl = baseUrl + "/page?pageSize={pageSize}&pageNo={pageNo}";

        ParameterizedTypeReference<List<CustomerDto>> responseType = new ParameterizedTypeReference<>() {};
        return restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                responseType,
                uriVariables
        );
    }

    public ResponseEntity<CustomerDto> getCustomerById(int id) {
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("id", String.valueOf(id));

        String requestUrl = baseUrl + "/{id}";

        return restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                CustomerDto.class,
                uriVariables
        );
    }

    public ResponseEntity<CustomerWithPostsDto> getCustomerWithPosts(int customerId) {
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("customerId", String.valueOf(customerId));

        String requestUrl = baseUrl + "/{customerId}/withPosts";

        return restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                CustomerWithPostsDto.class,
                uriVariables
        );
    }

    public ResponseEntity<Void> updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        HttpEntity<CustomerUpdateRequest> requestHttpEntity = new HttpEntity<>(customerUpdateRequest);

        Map<String, String> uriVariables= new HashMap<>();

        String requestUrl = baseUrl;

        return restTemplate.exchange(
                requestUrl,
                HttpMethod.PUT,
                requestHttpEntity,
                Void.class
        );
    }

    public ResponseEntity<Void> deleteCustomer(int id) {
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("id", String.valueOf(id));

        String requestUrl = baseUrl + "/{id}";

        return restTemplate.exchange(
                requestUrl,
                HttpMethod.DELETE,
                null,
                Void.class,
                uriVariables
        );
    }
}
