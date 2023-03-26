package com.emreoytun.customermanagementmw.business.abstracts;

import com.emreoytun.customermanagementmw.dto.customer.requests.CustomerAddRequest;
import com.emreoytun.customermanagementmw.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementmw.dto.customer.responses.CustomerGetResponse;

import java.util.List;

public interface CustomerService {
    void addCustomer(CustomerAddRequest customerAddDto);
    void deleteCustomer(int id);
    void updateCustomer(CustomerUpdateRequest customerUpdateDto);
    CustomerGetResponse getCustomerById(int id);
    List<CustomerGetResponse> getAllCustomers();
    List<CustomerGetResponse> getCustomersByPageNo(int pageSize, int pageNo);
}