package com.emreoytun.customermanagementmw.consumers;

import com.emreoytun.customermanagementdata.dto.post.PostDto;
import com.emreoytun.customermanagementdata.dto.post.request.PostAddRequest;
import com.emreoytun.customermanagementmw.constants.CustomerManagementConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@DependsOn("customer-management-constants")
public class PostConsumer {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public PostConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        baseUrl = CustomerManagementConstants.CUSTOMER_MANAGEMENT_BACKEND_BASE_URL + "/v1/posts";
    }

    public ResponseEntity<Void> addPost(PostAddRequest postAddRequest) {
        String requestUrl = baseUrl;
        HttpEntity<PostAddRequest> requestHttpEntity = new HttpEntity<>(postAddRequest);

        return restTemplate.exchange(
                requestUrl,
                HttpMethod.POST,
                requestHttpEntity,
                Void.class);
    }

    public ResponseEntity<List<PostDto>> getPostsByCustomerId(int customerId) {
        String requestUrl = baseUrl + "/getCustomerPosts?customerId=" + customerId;
        ParameterizedTypeReference<List<PostDto>> responseType = new ParameterizedTypeReference<>() {};

        return restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                responseType);
    }

    public ResponseEntity<Void> deleteAllByCustomerId(int customerId) {
        String requestUrl = baseUrl + "/deleteAllCustomerPosts?customerId=" + customerId;

        return restTemplate.exchange(
                requestUrl,
                HttpMethod.DELETE,
                null,
                Void.class);
    }
}
