package com.emreoytun.customermanagementmw.consumers;

import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerAddRequest;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementdata.dto.customer.responses.CustomerGetResponse;
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

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Void> addCustomer(CustomerAddRequest customerAddDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CustomerAddRequest> requestHttpEntity = new HttpEntity<>(customerAddDto, headers);
        return restTemplate.exchange(
                "http://localhost:8082/api/customers",
                HttpMethod.POST,
                requestHttpEntity,
                Void.class
        );
    }


    public ResponseEntity<Void> updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        HttpEntity<CustomerUpdateRequest> requestHttpEntity = new HttpEntity<>(customerUpdateRequest);

        return restTemplate.exchange(
                "http://localhost:8082/api/customers",
                HttpMethod.PUT,
                requestHttpEntity,
                Void.class
        );
    }

    public ResponseEntity<Void> deleteCustomer(int id) {
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("id", String.valueOf(id));

        return restTemplate.exchange(
                "http://localhost:8082/api/customers/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                uriVariables
        );
    }

    public ResponseEntity<CustomerGetResponse> getCustomerById(int id) {
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("id", String.valueOf(id));

        return restTemplate.exchange(
                "http://localhost:8082/api/customers/filter?id={id}",
                HttpMethod.GET,
                null,
                CustomerGetResponse.class,
                uriVariables
        );
    }

    public ResponseEntity<List<CustomerGetResponse>> getAllCustomers() {
        ParameterizedTypeReference<List<CustomerGetResponse>> responseType = new ParameterizedTypeReference<List<CustomerGetResponse>>() {};
        ResponseEntity<List<CustomerGetResponse>> responseEntity = restTemplate.exchange(
                "http://localhost:8082/api/customers",
                HttpMethod.GET,
                null,
                responseType);
        return responseEntity;
    }

    public ResponseEntity<List<CustomerGetResponse>> getAllCustomersByPage(int pageSize, int pageNo) {
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("pageSize", String.valueOf(pageSize));
        uriVariables.put("pageNo", String.valueOf(pageNo));

        ParameterizedTypeReference<List<CustomerGetResponse>> responseType = new ParameterizedTypeReference<List<CustomerGetResponse>>() {};
        return restTemplate.exchange(
                "http://localhost:8082/api/customers/page?pageSize={pageSize}&",
                HttpMethod.GET,
                null,
                responseType,
                uriVariables
        );
    }
}
