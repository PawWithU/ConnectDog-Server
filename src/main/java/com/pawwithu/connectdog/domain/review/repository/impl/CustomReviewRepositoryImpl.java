package com.pawwithu.connectdog.domain.review.repository.impl;

import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetResponse;
import com.pawwithu.connectdog.domain.review.repository.CustomReviewRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // 대표 이미지를 제외한 리뷰 이미지 조회
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
    public ReviewGetResponse getOneReview(Long id, Long reviewId) {
        return queryFactory
                .select(Projections.constructor(ReviewGetResponse.class,
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
}
