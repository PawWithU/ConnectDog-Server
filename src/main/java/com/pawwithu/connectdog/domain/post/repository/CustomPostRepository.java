package com.pawwithu.connectdog.domain.post.repository;

import com.pawwithu.connectdog.domain.post.dto.request.PostSearchRequest;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetHomeResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetOneResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostSearchResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPostRepository {

    List<PostGetHomeResponse> getHomePosts();
    List<PostSearchResponse> searchPosts(PostSearchRequest request, Pageable pageable);
    PostGetOneResponse getOnePost(Long volunteerId, Long postId);
    List<String> getOnePostImages(Long postId);
}
