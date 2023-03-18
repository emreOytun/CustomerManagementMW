package com.emreoytun.tutorial.business.rules;

import com.emreoytun.tutorial.business.exceptions.CustomerBusinessRulesException;
import com.emreoytun.tutorial.dao.abstracts.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Component/Service/Bean
@Component
public class CustomerBusinessRules {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerBusinessRules(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    // Customer must exist before since it should have been created already.
    public void checkCustomerExistsUpdate(int id) throws CustomerBusinessRulesException {
        if ( !customerDao.existsById(id) ) {
            throw new CustomerBusinessRulesException("Customer not found.");
        }
    }

    // Customer must not exist before.
    public void checkCustomerExistsCreate(String username) throws CustomerBusinessRulesException {
        if ( customerDao.existsByUserName(username) ) {
            throw new CustomerBusinessRulesException("Customer has already been created.");
        }
    }

    public void checkPageQueries(int pageSize, int pageNo) throws CustomerBusinessRulesException {
        if (pageNo <= 0) {
            throw new CustomerBusinessRulesException("Page no should not be smaller than 1.");
        }
        if (pageSize <= 0) {
            throw new CustomerBusinessRulesException("Page size should not be smaller than 1.");
        }
    }

}
