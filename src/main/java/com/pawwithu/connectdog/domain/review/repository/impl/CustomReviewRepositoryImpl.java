package com.pawwithu.connectdog.domain.review.repository.impl;

import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetReviewsResponse;
import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetAllResponse;
import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetOneResponse;
import com.pawwithu.connectdog.domain.review.repository.CustomReviewRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pawwithu.connectdog.domain.intermediary.entity.QIntermediary.intermediary;
import static com.pawwithu.connectdog.domain.post.entity.QPost.post;
import static com.pawwithu.connectdog.domain.review.entity.QReview.review;
import static com.pawwithu.connectdog.domain.review.entity.QReviewImage.reviewImage;
import static com.pawwithu.connectdog.domain.dog.entity.QDog.dog;
import static com.pawwithu.connectdog.domain.volunteer.entity.QVolunteer.volunteer;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomReviewRepositoryImpl implements CustomReviewRepository {

    private final JPAQueryFactory queryFactory;

    // 대표 이미지를 제외한 후기 이미지 조회
    @Override
    public List<String> getOneReviewImages(Long reviewId) {
        return queryFactory
                .select(reviewImage.image)
                .from(reviewImage)
                .join(reviewImage.review, review)
                .where(reviewImage.review.id.eq(reviewId)
                        .and(review.mainImage.id.ne(reviewImage.id)))
                .fetch();
    }

    // 후기 단건 조회 (대표 이미지를 제외한 다른 이미지 포함 X)
    @Override
    public ReviewGetOneResponse getOneReview(Long reviewId) {
        return queryFactory
                .select(Projections.constructor(ReviewGetOneResponse.class,
                        dog.name, volunteer.nickname, reviewImage.image,
                        post.startDate, post.endDate, post.departureLoc, post.arrivalLoc,
                        intermediary.name, review.content))
                .from(review)
                .join(review.volunteer, volunteer)
                .join(review.mainImage, reviewImage)
                .join(review.post, post)
                .join(review.post.dog, dog)
                .join(review.post.intermediary, intermediary)
                .where(review.id.eq(reviewId))
                .fetchOne();
    }

    // 후기 전체 조회
    @Override
    public List<ReviewGetAllResponse> getAllReviews(Pageable pageable) {
        List<ReviewGetAllResponse> reviews = queryFactory
                .select(Projections.constructor(ReviewGetAllResponse.class,
                        dog.name, volunteer.nickname, reviewImage.image,
                        post.startDate, post.endDate, post.departureLoc, post.arrivalLoc,
                        intermediary.name, review.content))
                .from(review)
                .join(review.volunteer, volunteer)
                .join(review.mainImage, reviewImage)
                .join(review.post, post)
                .join(review.post.dog, dog)
                .join(review.post.intermediary, intermediary)
                .orderBy(review.createdDate.desc())
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .fetch();

        return reviews;
    }

    // 이동봉사 중개 별 후기 조회 (최신 순)
    @Override
    public List<IntermediaryGetReviewsResponse> getIntermediaryReviews(Long intermediaryId, Pageable pageable) {
        List<IntermediaryGetReviewsResponse> reviews = queryFactory
                .select(Projections.constructor(IntermediaryGetReviewsResponse.class,
                        dog.name, volunteer.nickname, reviewImage.image,
                        post.startDate, post.endDate, post.departureLoc, post.arrivalLoc,
                        intermediary.name, review.content))
                .from(review)
                .where(review.post.intermediary.id.eq(intermediaryId))
                .join(review.volunteer, volunteer)
                .join(review.mainImage, reviewImage)
                .join(review.post, post)
                .join(review.post.dog, dog)
                .join(review.post.intermediary, intermediary)
                .orderBy(review.createdDate.desc())
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .fetch();

        return reviews;
    }

    // 받은 후기 총 건수
    @Override
    public Long getCountOfReviews(Long intermediaryId) {
        return queryFactory
                .select(review.count())
                .from(review)
                .where(review.post.intermediary.id.eq(intermediaryId))
                .join(review.post, post)
                .fetchOne();
    }
}
