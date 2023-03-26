package com.emreoytun.customermanagementmw.business.rules;

import com.emreoytun.customermanagementmw.business.exceptions.CustomerBusinessRulesException;
import com.emreoytun.customermanagementmw.dao.abstracts.CustomerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Component/Service/Bean
@Component
public class CustomerBusinessRules {

    private final CustomerDao customerDao;
    private final Logger logger = LoggerFactory.getLogger(CustomerBusinessRules.class);

    @Autowired
    public CustomerBusinessRules(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    // Customer must exist before since it should have been created already.
    public void checkCustomerExistsUpdate(int id) throws CustomerBusinessRulesException {
        logger.info("Entering checkCustomerExistsUpdate method");
        if ( !customerDao.existsById(id) ) {
            logger.error("Customer is not found");
            throw new CustomerBusinessRulesException("Customer not found.");
        }
    }

    // Customer must not exist before.
    public void checkCustomerExistsCreate(String username) throws CustomerBusinessRulesException {
        logger.info("Entering checkCustomerExistsCreate method");
        if ( customerDao.existsByUserName(username) ) {
            logger.error("Customer has already been created.");
            throw new CustomerBusinessRulesException("Customer has already been created.");
        }
    }

    public void checkPageQueries(int pageSize, int pageNo) throws CustomerBusinessRulesException {
        logger.info("Entering checkPageQueries method");
        if (pageNo <= 0) {
            logger.error("Page no should not be smaller than 1.");
            throw new CustomerBusinessRulesException("Page no should not be smaller than 1.");
        }
        if (pageSize <= 0) {
            logger.error("Page size should not be smaller than 1.");
            throw new CustomerBusinessRulesException("Page size should not be smaller than 1.");
        }
    }

}
