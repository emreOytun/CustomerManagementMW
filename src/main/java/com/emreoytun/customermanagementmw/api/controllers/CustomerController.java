package com.emreoytun.customermanagementmw.api.controllers;

import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerAddRequest;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementmw.business.abstracts.CustomerService;
import com.emreoytun.customermanagementdata.dto.customer.responses.CustomerGetResponse;
import javax.validation.Valid;

import com.emreoytun.customermanagementmw.consumers.CustomerConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//localhost:8080/api/customers/
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    // SOLID
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerConsumer customerConsumer;

    // JWT - HEADER -> "Authorization": Bearer .....

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid CustomerAddRequest customerAddDto) {
        //customerService.addCustomer(customerAddDto);
        customerConsumer.addCustomer(customerAddDto);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict("CUSTOMERS")
    public void updateCustomer(@RequestBody @Valid CustomerUpdateRequest customerUpdateDto) {
        //customerService.updateCustomer(customerUpdateDto);
        customerConsumer.updateCustomer(customerUpdateDto);
    }

    // TODO: ONLY-ADMIN
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") int id) {
        //customerService.deleteCustomer(id);
        customerConsumer.deleteCustomer(id);
    }

    // /customers/3
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public CustomerGetResponse getCustomerById(@RequestParam(value = "id", required = true) int id) {
        //return customerService.getCustomerById(id);
        return customerConsumer.getCustomerById(id).getBody();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Cacheable("CUSTOMERS")
    public List<CustomerGetResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerGetResponse> getCustomersByPage(@RequestParam(value = "pageSize", required = true) int pageSize,
                                             @RequestParam(value = "pageNo", required = true) int pageNo) {

        return customerService.getCustomersByPageNo(pageSize, pageNo);
    }
}
