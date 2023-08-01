package com.emreoytun.customermanagementmw.controllers;

import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerAddRequest;
import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementdata.entities.User;
import com.emreoytun.customermanagementdata.repository.CustomerDao;
import com.emreoytun.customermanagementdata.repository.UserDao;
import com.emreoytun.customermanagementdata.validation.pageno.PageNoConstraint;
import com.emreoytun.customermanagementdata.validation.pagesize.PageSizeConstraint;
import com.emreoytun.customermanagementmw.service.customer.CustomerService;
import com.emreoytun.customermanagementdata.dto.customer.responses.CustomerGetResponse;
import jakarta.validation.Valid;

import com.emreoytun.customermanagementmw.consumers.CustomerConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;
    private CustomerConsumer customerConsumer;
    private final CustomerDao customerDao;

    private final UserDao userDao;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerConsumer customerConsumer,
                              CustomerDao customerDao, UserDao userDao) {
        this.customerConsumer = customerConsumer;
        this.customerService = customerService;
        this.customerDao = customerDao;
        this.userDao = userDao;
    }


    @GetMapping("/getUsers")
    public List<User> getAllUsers() {
        var u = SecurityContextHolder.getContext().getAuthentication();
        u.getAuthorities().forEach(System.out::println);
        return userDao.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid CustomerAddRequest customerAddDto) {
        customerService.addCustomer(customerAddDto);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody @Valid CustomerUpdateRequest customerUpdateDto) {
        customerService.updateCustomer(customerUpdateDto);
    }

    // TODO: ONLY-ADMIN
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") int id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public CustomerGetResponse getCustomerById(@RequestParam(value = "id", required = true) int id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerGetResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerGetResponse> getCustomersByPage(
            @RequestParam(value = "pageSize", required = true) @PageSizeConstraint int pageSize,
            @RequestParam(value = "pageNo", required = true) @PageNoConstraint int pageNo) {

        return customerService.getCustomersByPageNo(pageSize, pageNo);
    }
}
