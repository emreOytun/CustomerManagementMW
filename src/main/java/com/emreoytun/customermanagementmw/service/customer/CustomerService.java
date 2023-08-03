package com.emreoytun.customermanagementmw.service.customer;


import com.emreoytun.customermanagementdata.dto.customer.CustomerWithPostsDto;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementdata.dto.customer.CustomerDto;

import java.util.List;

public interface CustomerService {
    void deleteCustomer(int id);
    void updateCustomer(CustomerUpdateRequest customerUpdateDto, int currentUserId);
    CustomerDto getCustomerById(int id);
    List<CustomerDto> getAllCustomers();
    List<CustomerDto> getCustomersByPageNo(int pageSize, int pageNo);

    CustomerWithPostsDto getWithPosts(int customerId);
}