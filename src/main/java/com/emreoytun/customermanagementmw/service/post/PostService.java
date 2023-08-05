package com.emreoytun.customermanagementmw.service.post;

import com.emreoytun.customermanagementdata.dto.post.PostDto;
import com.emreoytun.customermanagementdata.dto.post.request.PostAddRequest;

import java.util.List;

public interface PostService {
    void addPost(PostAddRequest postAddRequest);

    List<PostDto> getPostsByCustomerId(int customerId);

    void deleteAllByCustomerId(int customerId);
}
