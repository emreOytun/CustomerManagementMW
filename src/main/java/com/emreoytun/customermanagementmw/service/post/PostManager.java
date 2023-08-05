package com.emreoytun.customermanagementmw.service.post;

import com.emreoytun.customermanagementdata.dto.post.PostDto;
import com.emreoytun.customermanagementdata.dto.post.request.PostAddRequest;
import com.emreoytun.customermanagementmw.consumers.PostConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostManager implements PostService {

    private final PostConsumer postConsumer;

    @Override
    public void addPost(PostAddRequest postAddRequest) {
        postConsumer.addPost(postAddRequest);
    }

    @Override
    public List<PostDto> getPostsByCustomerId(int customerId) {
        return postConsumer.getPostsByCustomerId(customerId).getBody();
    }

    @Override
    public void deleteAllByCustomerId(int customerId) {
        postConsumer.deleteAllByCustomerId(customerId);
    }
}
