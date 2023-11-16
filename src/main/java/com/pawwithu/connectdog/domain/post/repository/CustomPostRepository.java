package com.pawwithu.connectdog.domain.post.repository;

import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetPostsResponse;
import com.pawwithu.connectdog.domain.post.dto.request.PostSearchRequest;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetHomeResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetOneResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostRecruitingGetResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostSearchResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPostRepository {

    // 홈 화면 공고 5개 조회
    List<PostGetHomeResponse> getHomePosts();
    // 공고 필터 검색
    List<PostSearchResponse> searchPosts(PostSearchRequest request, Pageable pageable);
    // 대표 이미지를 제외한 공고 이미지 조회
    List<String> getOnePostImages(Long postId);
    // 공고 상세 조회 (대표 이미지를 제외한 다른 이미지 포함 X)
    PostGetOneResponse getOnePost(Long volunteerId, Long postId);
    List<PostRecruitingGetResponse> getRecruitingPosts(Long intermediaryId, Pageable pageable);
    List<IntermediaryGetPostsResponse> getIntermediaryPosts(Long intermediaryId, Pageable pageable);
    // 봉사 완료 건수
    Long getCountOfCompletedPosts(Long intermediaryId);
}
