package com.emreoytun.customermanagementmw.controllers;

import com.emreoytun.customermanagementdata.dto.customer.requests.CustomerUpdateRequest;
import com.emreoytun.customermanagementdata.dto.customer.CustomerWithPostsDto;
import com.emreoytun.customermanagementdata.validation.pageno.PageNoConstraint;
import com.emreoytun.customermanagementdata.validation.pagesize.PageSizeConstraint;
import com.emreoytun.customermanagementmw.annotations.security.CurrentUserId;
import com.emreoytun.customermanagementmw.service.customer.CustomerService;
import com.emreoytun.customermanagementdata.dto.customer.CustomerDto;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getCustomersByPage(
            @RequestParam(value = "pageSize") @PageSizeConstraint int pageSize,
            @RequestParam(value = "pageNo") @PageNoConstraint int pageNo) {

        return customerService.getCustomersByPageNo(pageSize, pageNo);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDto getCustomerById(@PathVariable(value = "id") int id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/{id}/withPosts")
    @ResponseStatus(HttpStatus.OK)
    public CustomerWithPostsDto getWithPosts(@PathVariable(value = "id") int customerId) {
        return customerService.getWithPosts(customerId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody @Valid CustomerUpdateRequest customerUpdateDto, @CurrentUserId int currentUserId) {
        customerService.updateCustomer(customerUpdateDto, currentUserId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@CurrentUserId int currentUserId) {
        customerService.deleteCustomer(currentUserId);
    }
}
