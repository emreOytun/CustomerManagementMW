package com.emreoytun.customermanagementmw.service.customer;

import com.emreoytun.customermanagementdata.dto.customer.CustomerWithPostsDto;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementdata.dto.customer.CustomerDto;
import com.emreoytun.customermanagementdata.exceptions.EntityNotFoundException;
import com.emreoytun.customermanagementmw.consumers.CustomerConsumer;
import com.emreoytun.customermanagementdata.exceptions.CustomException;
import com.emreoytun.customermanagementmw.cache.CacheService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerManager implements CustomerService {
    private final CustomerConsumer customerConsumer;
    private final Logger logger = LoggerFactory.getLogger(CustomerManager.class);

    @Override
    public List<CustomerDto> getAllCustomers() {
        ResponseEntity<List<CustomerDto>> response = customerConsumer.getAllCustomers();
        List<CustomerDto> customerList = response.getBody();
        return customerList;
    }

    @Override
    public List<CustomerDto> getCustomersByPageNo(int pageSize, int pageNo) throws CustomException {
        ResponseEntity<List<CustomerDto>> response = customerConsumer.getAllCustomersByPage(pageSize, pageNo);
        List<CustomerDto> resultList = response.getBody();
        return resultList;
    }

    @Override
    public CustomerDto getCustomerById(int id) throws CustomException {
        ResponseEntity<CustomerDto> response = customerConsumer.getCustomerById(id);
        CustomerDto customerGetDto = response.getBody();
        if (customerGetDto == null) {
            String errorMsg = "There is no such customer with id : " + id;
            logger.error(errorMsg);
            throw new EntityNotFoundException(errorMsg);
        }
        return customerGetDto;
    }

    @Override
    public CustomerWithPostsDto getWithPosts(int customerId) {
        ResponseEntity<CustomerWithPostsDto> response = customerConsumer.getCustomerWithPosts(customerId);
        CustomerWithPostsDto customerWithPostsDto = response.getBody();
        if (customerWithPostsDto == null) {
            String errorMsg = "There is no such customer with id : " + customerId;
            logger.error(errorMsg);
            throw new EntityNotFoundException(errorMsg);
        }
        return response.getBody();
    }

    @Override
    public void updateCustomer(CustomerUpdateRequest customerUpdateDto, int currentUserId) throws CustomException {
        // Set the id as the current user id to be updated.
        customerUpdateDto.setId(currentUserId);
        customerConsumer.updateCustomer(customerUpdateDto);
    }

    @Override
    public void deleteCustomer(int id) throws CustomException {
        customerConsumer.deleteCustomer(id);
    }

}