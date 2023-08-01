package com.emreoytun.customermanagementmw.service.customer;

import com.emreoytun.customermanagementdata.dto.IModelMapperService;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerAddRequest;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementdata.dto.customer.responses.CustomerGetResponse;
import com.emreoytun.customermanagementdata.repository.PostDao;
import com.emreoytun.customermanagementmw.consumers.CustomerConsumer;
import com.emreoytun.customermanagementmw.exceptions.CustomerBusinessRulesException;
import com.emreoytun.customermanagementmw.helper.HttpStatusChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class CustomerManager implements CustomerService {

    private final CustomerServiceRules businessRules;
    private final PostDao postDao;
    private final IModelMapperService modelMapperService;
    private final CustomerConsumer customerConsumer;
    private final Logger logger = LoggerFactory.getLogger(CustomerManager.class);

    @Autowired
    public CustomerManager(PostDao postDao, CustomerServiceRules customerServiceRules,
                           IModelMapperService modelMapperService, CustomerConsumer customerConsumer) {
        this.businessRules = customerServiceRules;
        this.modelMapperService = modelMapperService;
        this.postDao = postDao;
        this.customerConsumer = customerConsumer;
    }

    @Override
    public void addCustomer(CustomerAddRequest customerAddDto) throws CustomerBusinessRulesException {
        logger.info("Entering addCustomer method");

        businessRules.checkCustomerExistsCreate(customerAddDto.getUserName());
        customerConsumer.addCustomer(customerAddDto);

        logger.info("Returning addCustomer method");
    }

    @Override
    @Transactional
    public void deleteCustomer(int id) throws CustomerBusinessRulesException {
        logger.info("Entering deleteCustomer method");

        businessRules.checkCustomerExistsUpdate(id);
        postDao.deleteAllByCustomerId(id); // delete posts before deleting the customer
        customerConsumer.deleteCustomer(id);

        logger.info("Customer with id " + id + " is deleted");
    }

    @Override
    public void updateCustomer(CustomerUpdateRequest customerUpdateDto) throws CustomerBusinessRulesException {
        logger.info("Entering updateCustomer method");

        businessRules.checkCustomerExistsUpdate(customerUpdateDto.getId());
        customerConsumer.updateCustomer(customerUpdateDto);

        logger.info("Customer with id " + customerUpdateDto.getId() + " is updated");
    }

    /* We can specify with which parameters key is constructed in @Cacheable annotation. */
    @Override
    public CustomerGetResponse getCustomerById(int id) throws CustomerBusinessRulesException {
        logger.info("Entering getCustomerById method");

        businessRules.checkCustomerExistsUpdate(id);
        ResponseEntity<CustomerGetResponse> response = customerConsumer.getCustomerById(id);

        if (!HttpStatusChecker.checkIfHttpOk(response.getStatusCode())) {
            logger.error("There is an error while fetching the customer.");
            throw new CustomerBusinessRulesException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CustomerGetResponse customerGetDto = response.getBody();
        if (customerGetDto == null) {
            throw new CustomerBusinessRulesException("Customer not found", HttpStatus.NOT_FOUND);
        }

        logger.info("Returning getCustomerById method");
        return customerGetDto;
    }

    @Override
    public List<CustomerGetResponse> getAllCustomers() {
        logger.info("Entering getAllCustomers method");

        ResponseEntity<List<CustomerGetResponse>> response = customerConsumer.getAllCustomers();
        if (!HttpStatusChecker.checkIfHttpOk(response.getStatusCode())) {
            logger.error("There is an error while fetching the customers.");
            throw new CustomerBusinessRulesException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<CustomerGetResponse> customerList = response.getBody();

        logger.info("Returning getAllCustomers method");
        return customerList;
    }

    @Override
    public List<CustomerGetResponse> getCustomersByPageNo(int pageSize, int pageNo) throws CustomerBusinessRulesException {
        logger.info("Entering getCustomersByPageNo method");

        businessRules.checkPageQueries(pageSize, pageNo);

        ResponseEntity<List<CustomerGetResponse>> response = customerConsumer.getAllCustomersByPage(pageSize, pageNo);
        if (!HttpStatusChecker.checkIfHttpOk(response.getStatusCode())) {
            logger.error("There is an error while fetching the customers.");
            throw new CustomerBusinessRulesException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<CustomerGetResponse> resultList = response.getBody();

        logger.info("Returning getCustomersByPageNo method");
        return resultList;
    }
}