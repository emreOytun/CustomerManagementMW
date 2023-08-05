package com.emreoytun.customermanagementmw.controllers;

import com.emreoytun.customermanagementdata.dto.post.PostDto;
import com.emreoytun.customermanagementdata.dto.post.request.PostAddRequest;
import com.emreoytun.customermanagementmw.annotations.security.CurrentUserId;
import com.emreoytun.customermanagementmw.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPost(@CurrentUserId int customerId, @RequestBody PostAddRequest postAddRequest) {
        postAddRequest.setCustomerID(customerId);
        postService.addPost(postAddRequest);
    }

    @GetMapping("/getCustomerPosts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getPostsByCustomerId(@RequestParam("customerId") int customerId) {
        return postService.getPostsByCustomerId(customerId);
    }

    @DeleteMapping("/deleteAllCustomerPosts")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllByCustomerId(@CurrentUserId int customerId) {
        postService.deleteAllByCustomerId(customerId);
    }

}
