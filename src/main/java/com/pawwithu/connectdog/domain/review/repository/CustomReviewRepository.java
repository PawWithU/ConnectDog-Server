package com.pawwithu.connectdog.domain.review.repository;

import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetResponse;

import java.util.List;

public interface CustomReviewRepository {
    // 리뷰 상세 조회 (대표 이미지를 제외한 다른 이미지 포함 X)
    ReviewGetResponse getOneReview(Long id, Long reviewId);
    // 대표 이미지를 제외한 리뷰 이미지 조회
    List<String> getOneReviewImages(Long reviewId);
}
