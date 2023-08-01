package com.emreoytun.customermanagementmw.consumers;

import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerAddRequest;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementdata.dto.customer.responses.CustomerGetResponse;
import com.emreoytun.customermanagementmw.constants.CustomerManagementConstants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
        baseUrl = CustomerManagementConstants.CUSTOMER_MANAGEMENT_BACKEND_BASE_URL + "/customers";
    }

    public ResponseEntity<Void> addCustomer(CustomerAddRequest customerAddDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestUrl = baseUrl;

        HttpEntity<CustomerAddRequest> requestHttpEntity = new HttpEntity<>(customerAddDto, headers);
        return restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                requestHttpEntity,
                Void.class
        );
    }


    public ResponseEntity<Void> updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        HttpEntity<CustomerUpdateRequest> requestHttpEntity = new HttpEntity<>(customerUpdateRequest);

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

    public ResponseEntity<CustomerGetResponse> getCustomerById(int id) {
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("id", String.valueOf(id));

        String requestUrl = baseUrl + "/filter?id={id}";

        return restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                CustomerGetResponse.class,
                uriVariables
        );
    }

    public ResponseEntity<List<CustomerGetResponse>> getAllCustomers() {
        ParameterizedTypeReference<List<CustomerGetResponse>> responseType = new ParameterizedTypeReference<List<CustomerGetResponse>>() {};

        String requestUrl = baseUrl;

        ResponseEntity<List<CustomerGetResponse>> responseEntity = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                responseType);
        return responseEntity;
    }

    public ResponseEntity<List<CustomerGetResponse>> getAllCustomersByPage(int pageSize, int pageNo) {
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("pageSize", String.valueOf(pageSize));
        uriVariables.put("pageNo", String.valueOf(pageNo));

        String requestUrl = baseUrl + "/page?pageSize={pageSize}&pageNo={pageNo}";

        ParameterizedTypeReference<List<CustomerGetResponse>> responseType = new ParameterizedTypeReference<List<CustomerGetResponse>>() {};
        return restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                responseType,
                uriVariables
        );
    }
}
