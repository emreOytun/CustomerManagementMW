package com.emreoytun.tutorial.api.controllers;

import com.emreoytun.tutorial.business.abstracts.CustomerService;
import com.emreoytun.tutorial.entities.concretes.Customer;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    // JWT - HEADER -> "Authorization": Bearer .....

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid Customer customer) {
        customerService.add(customer);
    }

    // /customers/3
    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(@RequestParam(value = "id", required = true) int id) {
        return customerService.getById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return customerService.getAll();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody @Valid Customer customer) {
        customerService.update(customer);
    }

    // TODO: ONLY-ADMIN
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") int id) {
        customerService.delete(id);
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getCustomersByPage(@RequestParam(value = "pageSize", required = true) int pageSize,
                                             @RequestParam(value = "pageNo", required = true) int pageNo) {

        return customerService.getCustomersByPageNo(pageSize, pageNo);
    }
}
