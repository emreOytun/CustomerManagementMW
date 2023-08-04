package com.emreoytun.customermanagementmw.service.customer;

import com.emreoytun.customermanagementdata.dto.customer.CustomerWithPostsDto;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementdata.dto.customer.CustomerDto;
import com.emreoytun.customermanagementdata.exceptions.EntityNotFoundException;
import com.emreoytun.customermanagementmw.consumers.CustomerConsumer;
import com.emreoytun.customermanagementdata.exceptions.CustomerBusinessRulesException;
import com.emreoytun.customermanagementmw.helper.HttpStatusChecker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
        logger.info("Entering getAllCustomers method");

        ResponseEntity<List<CustomerDto>> response = customerConsumer.getAllCustomers();
        if (!HttpStatusChecker.checkIfHttpOk(response.getStatusCode())) {
            logger.error("There is an error while fetching the customers.");
            throw new CustomerBusinessRulesException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<CustomerDto> customerList = response.getBody();
        logger.info("Returning getAllCustomers method");
        return customerList;
    }

    @Override
    public List<CustomerDto> getCustomersByPageNo(int pageSize, int pageNo) throws CustomerBusinessRulesException {
        logger.info("Entering getCustomersByPageNo method");

        ResponseEntity<List<CustomerDto>> response = customerConsumer.getAllCustomersByPage(pageSize, pageNo);
        if (!HttpStatusChecker.checkIfHttpOk(response.getStatusCode())) {
            logger.error("There is an error while fetching the customers.");
            throw new CustomerBusinessRulesException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<CustomerDto> resultList = response.getBody();
        logger.info("Returning getCustomersByPageNo method");
        return resultList;
    }

    @Override
    public CustomerDto getCustomerById(int id) throws CustomerBusinessRulesException {
        logger.info("Entering getCustomerById method");

        ResponseEntity<CustomerDto> response = customerConsumer.getCustomerById(id);

        if (!HttpStatusChecker.checkIfHttpOk(response.getStatusCode())) {
            logger.error("There is an error while fetching the customer with id : " + id);
            throw new CustomerBusinessRulesException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CustomerDto customerGetDto = response.getBody();
        if (customerGetDto == null) {
            String errorMsg = "There is no such customer with id : " + id;
            logger.error(errorMsg);
            throw new EntityNotFoundException(errorMsg);
        }

        logger.info("Returning getCustomerById method");
        return customerGetDto;
    }

    @Override
    public CustomerWithPostsDto getWithPosts(int customerId) {
        ResponseEntity<CustomerWithPostsDto> response = customerConsumer.getCustomerWithPosts(customerId);

        if (!HttpStatusChecker.checkIfHttpOk(response.getStatusCode())) {
            logger.error("There is an error while fetching the customer with id : " + customerId);
            throw new CustomerBusinessRulesException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CustomerWithPostsDto customerWithPostsDto = response.getBody();
        if (customerWithPostsDto == null) {
            String errorMsg = "There is no such customer with id : " + customerId;
            logger.error(errorMsg);
            throw new EntityNotFoundException(errorMsg);
        }

        logger.info("Returning getWithPosts method");
        return response.getBody();
    }

    @Override
    public void updateCustomer(CustomerUpdateRequest customerUpdateDto, int currentUserId) throws CustomerBusinessRulesException {
        logger.info("Entering updateCustomer method");

        // Set the id as the current user id to be updated.
        customerUpdateDto.setId(currentUserId);

        ResponseEntity<Void> response = customerConsumer.updateCustomer(customerUpdateDto);
        if (!HttpStatusChecker.checkIfHttpOk(response.getStatusCode())) {
            logger.error("There is an error while updating the customer : " + customerUpdateDto);
            throw new CustomerBusinessRulesException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Customer with id " + customerUpdateDto.getId() + " is updated");
    }

    @Override
    public void deleteCustomer(int id) throws CustomerBusinessRulesException {
        logger.info("Entering deleteCustomer method");

        ResponseEntity<Void> response = customerConsumer.deleteCustomer(id);
        if (!HttpStatusChecker.checkIfHttpOk(response.getStatusCode())) {
            logger.error("There is an error while deleting the customer with id : " + id);
            throw new CustomerBusinessRulesException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("Customer with id " + id + " is deleted");
    }

}