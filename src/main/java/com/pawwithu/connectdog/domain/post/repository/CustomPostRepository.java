package com.pawwithu.connectdog.domain.post.repository;

import com.pawwithu.connectdog.domain.post.dto.response.PostGetHomeResponse;

import java.util.List;

public interface CustomPostRepository {

    List<PostGetHomeResponse> getHomePosts();
}
