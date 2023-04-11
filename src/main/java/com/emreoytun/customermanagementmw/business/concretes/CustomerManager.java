package com.emreoytun.customermanagementmw.business.concretes;

import com.emreoytun.customermanagementdata.dto.IModelMapperService;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerAddRequest;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementdata.dto.customer.responses.CustomerGetResponse;
import com.emreoytun.customermanagementmw.business.abstracts.CustomerService;
import com.emreoytun.customermanagementmw.business.exceptions.CustomerBusinessRulesException;
import com.emreoytun.customermanagementmw.business.rules.CustomerBusinessRules;
import com.emreoytun.customermanagementmw.dao.abstracts.CustomerDao;
import com.emreoytun.customermanagementmw.dao.abstracts.PostDao;
import com.emreoytun.customermanagementmw.entities.concretes.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerManager implements CustomerService {

    // SOLID -> D: Dependency Injection
    // Factory Design
    // Reference: Objenin memory'deki adresi

    private final CustomerBusinessRules businessRules;
    private final CustomerDao customerDao;
    private final PostDao postDao;
    private final IModelMapperService modelMapperService;
    private final Logger logger = LoggerFactory.getLogger(CustomerManager.class);

    // Dependency-injection
    @Autowired
    public CustomerManager(CustomerDao customerDao, PostDao postDao, CustomerBusinessRules customerBusinessRules,
                           IModelMapperService modelMapperService) {
        this.customerDao = customerDao;
        this.businessRules = customerBusinessRules;
        this.modelMapperService = modelMapperService;
        this.postDao = postDao;
    }

    @Override
    public void addCustomer(CustomerAddRequest customerAddDto) throws CustomerBusinessRulesException {
        logger.info("Entering addCustomer method");

        businessRules.checkCustomerExistsCreate(customerAddDto.getUserName());

        Customer customer = modelMapperService.map(customerAddDto, Customer.class);
        customerDao.save(customer);
        logger.info("Customer with id " + customer.getId() + " and username " + customer.getUserName()
                + " is deleted");
    }

    @Override
    @Transactional
    public void deleteCustomer(int id) throws CustomerBusinessRulesException {
        logger.info("Entering deleteCustomer method");

        businessRules.checkCustomerExistsUpdate(id);
        postDao.deleteAllByCustomerId(id); // delete posts before deleting the customer
        customerDao.deleteById(id);
        logger.info("Customer with id " + id + " is deleted");
    }

    @Override
    public void updateCustomer(CustomerUpdateRequest customerUpdateDto) throws CustomerBusinessRulesException {
        logger.info("Entering updateCustomer method");

        businessRules.checkCustomerExistsUpdate(customerUpdateDto.getId());

        Customer customer = customerDao.findById(customerUpdateDto.getId());
        modelMapperService.copyProperties(customerUpdateDto, customer);
        customerDao.save(customer);
        logger.info("Customer with id " + customer.getId() + " is updated");
    }

    @Override
    public CustomerGetResponse getCustomerById(int id) throws CustomerBusinessRulesException {
        logger.info("Entering getCustomerById method");

        businessRules.checkCustomerExistsUpdate(id);
        Customer customer = customerDao.findById(id);
        CustomerGetResponse customerGetDto = modelMapperService.map(customer, CustomerGetResponse.class);
        return customerGetDto;
    }

    @Override
    public List<CustomerGetResponse> getAllCustomers() {
        logger.info("Entering getAllCustomers method");

        List<Customer> customerList = customerDao.findAll();
        List<CustomerGetResponse> resultList = customerList.stream()
                .map(customer -> modelMapperService.map(customer, CustomerGetResponse.class)).toList();

        return resultList;
    }

    @Override
    public List<CustomerGetResponse> getCustomersByPageNo(int pageSize, int pageNo) throws CustomerBusinessRulesException {
        logger.info("Entering getCustomersByPageNo method");

        businessRules.checkPageQueries(pageSize, pageNo);

        // In Spring, pages start from 0.
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        List<Customer> customerList = customerDao.findAll(pageable).getContent();
        List<CustomerGetResponse> resultList = customerList.stream()
                .map(customer -> modelMapperService.map(customer, CustomerGetResponse.class)).toList();
        return resultList;
    }
}