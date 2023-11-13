package com.pawwithu.connectdog.domain.review.repository;

import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetAllResponse;
import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetOneResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomReviewRepository {

    // 대표 이미지를 제외한 리뷰 이미지 조회
    List<String> getOneReviewImages(Long reviewId);
    // 리뷰 단건 조회 (대표 이미지를 제외한 다른 이미지 포함 X)
    ReviewGetOneResponse getOneReview(Long id, Long reviewId);

    // 리뷰 전체 조회 (최신 순)
    List<ReviewGetAllResponse> getAllReviews(Pageable pageable);
}
