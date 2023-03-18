package com.emreoytun.customermanagementmw.business.abstracts;

import com.emreoytun.customermanagementmw.entities.concretes.Customer;

import java.util.List;

public interface CustomerService extends EntityService<Customer> {
    List<Customer> getCustomersByPageNo(int pageSize, int pageNo);
}
