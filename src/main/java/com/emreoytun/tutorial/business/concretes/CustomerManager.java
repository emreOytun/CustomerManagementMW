package com.emreoytun.tutorial.business.concretes;

import com.emreoytun.tutorial.business.abstracts.CustomerService;
import com.emreoytun.tutorial.business.abstracts.EntityService;
import com.emreoytun.tutorial.business.exceptions.CustomerBusinessRulesException;
import com.emreoytun.tutorial.business.rules.CustomerBusinessRules;
import com.emreoytun.tutorial.dao.abstracts.CustomerDao;
import com.emreoytun.tutorial.entities.concretes.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerManager implements CustomerService {

    // SOLID -> D: Dependency Injection
    // Factory Design
    // Reference: Objenin memory'deki adresi

    private final CustomerBusinessRules businessRules;
    private final CustomerDao customerDao;

    // Dependency-injection
    @Autowired
    public CustomerManager(CustomerDao customerDao, CustomerBusinessRules customerBusinessRules) {
        this.customerDao = customerDao;
        this.businessRules = customerBusinessRules;
    }

    @Override
    public void add(Customer customer) throws CustomerBusinessRulesException {
        businessRules.checkCustomerExistsCreate(customer.getUserName());
        customerDao.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerDao.findAll();
    }

    @Override
    public void delete(int id) throws CustomerBusinessRulesException {
        businessRules.checkCustomerExistsUpdate(id);
        customerDao.deleteById(id);
    }

    @Override
    public void update(Customer customer) throws CustomerBusinessRulesException {
        businessRules.checkCustomerExistsUpdate(customer.getId());
        customerDao.save(customer);
    }

    @Override
    public Customer getById(int id) throws CustomerBusinessRulesException {
        businessRules.checkCustomerExistsUpdate(id);
        return customerDao.findById(id);
    }

    @Override
    public List<Customer> getCustomersByPageNo(int pageSize, int pageNo) throws CustomerBusinessRulesException {
        businessRules.checkPageQueries(pageSize, pageNo);
        // In Spring, pages start from 0.
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return customerDao.findAll(pageable).getContent();
    }
}