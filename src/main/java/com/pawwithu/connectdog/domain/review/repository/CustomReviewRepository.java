package com.pawwithu.connectdog.domain.review.repository;

import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetReviewsResponse;
import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetAllResponse;
import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetOneResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomReviewRepository {

    // 대표 이미지를 제외한 후기 이미지 조회
    List<String> getOneReviewImages(Long reviewId);

    // 후기 단건 조회 (대표 이미지를 제외한 다른 이미지 포함 X)
    ReviewGetOneResponse getOneReview(Long reviewId);

    // 후기 전체 조회 (최신 순)
    List<ReviewGetAllResponse> getAllReviews(Pageable pageable);

    // 이동봉사 중개 별 후기 조회 (최신 순)
    List<IntermediaryGetReviewsResponse> getIntermediaryReviews(Long intermediaryId, Pageable pageable);

    // 받은 후기 총 건수
    Long getIntermediaryCountOfReviews(Long intermediaryId);

    // 봉사 후기 건수
    Long getVolunteerCountOfReviews(Long id);
}
