package com.emreoytun.customermanagementmw.controllers;

import com.emreoytun.customermanagementdata.dto.post.request.PostAddRequest;
import com.emreoytun.customermanagementdata.entities.Customer;
import com.emreoytun.customermanagementdata.entities.Post;
import com.emreoytun.customermanagementdata.repository.CustomerDao;
import com.emreoytun.customermanagementdata.repository.PostDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/{customerId}/posts")
public class PostController {

    /*
    Customer - Post -> One - Many
    /api/customers/{customerId}/posts/... -> User
     */

    private PostDao postDao;
    private CustomerDao customerDao;

    @Autowired
    public PostController(PostDao postDao, CustomerDao customerDao) {
        this.postDao = postDao;
        this.customerDao = customerDao;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@PathVariable("customerId") int customerId, PostAddRequest postDto) {
        Customer customer = customerDao.findById(customerId);
        if (customer == null) {
            throw new RuntimeException();
        }

        Post post = new Post();
        post.setId(customerId); // TODO:BAK
        BeanUtils.copyProperties(postDto, post);

        post.setCustomer(customer);
        postDao.save(post);
    }

}
