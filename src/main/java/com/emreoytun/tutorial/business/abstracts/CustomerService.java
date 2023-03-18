package com.emreoytun.tutorial.business.abstracts;

import com.emreoytun.tutorial.entities.concretes.Customer;

import java.util.List;

public interface CustomerService extends EntityService<Customer> {
    List<Customer> getCustomersByPageNo(int pageSize, int pageNo);
}
