package com.pawwithu.connectdog.domain.application.repository.impl;

import com.pawwithu.connectdog.domain.application.dto.response.*;
import com.pawwithu.connectdog.domain.application.entity.Application;
import com.pawwithu.connectdog.domain.application.entity.ApplicationStatus;
import com.pawwithu.connectdog.domain.application.repository.CustomApplicationRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pawwithu.connectdog.domain.application.entity.QApplication.application;
import static com.pawwithu.connectdog.domain.dog.entity.QDog.dog;
import static com.pawwithu.connectdog.domain.dogStatus.entity.QDogStatus.dogStatus;
import static com.pawwithu.connectdog.domain.intermediary.entity.QIntermediary.intermediary;
import static com.pawwithu.connectdog.domain.post.entity.QPost.post;
import static com.pawwithu.connectdog.domain.post.entity.QPostImage.postImage;
import static com.pawwithu.connectdog.domain.review.entity.QReview.review;
import static com.pawwithu.connectdog.domain.volunteer.entity.QVolunteer.volunteer;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomApplicationRepositoryImpl implements CustomApplicationRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ApplicationVolunteerWaitingResponse> getVolunteerWaitingApplications(Long volunteerId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ApplicationVolunteerWaitingResponse.class,
                        post.id, postImage.image, post.departureLoc, post.arrivalLoc, post.startDate, post.endDate,
                        intermediary.name, post.isKennel, application.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.intermediary, intermediary)
                .join(application.post.mainImage, postImage)
                .where(application.status.eq(ApplicationStatus.WAITING)
                        .and(application.volunteer.id.eq(volunteerId)))
                .orderBy(application.createdDate.desc())    // 신청 최신순
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    @Override
    public List<ApplicationVolunteerProgressingResponse> getVolunteerProgressingApplications(Long volunteerId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ApplicationVolunteerProgressingResponse.class,
                        post.id, postImage.image, post.departureLoc, post.arrivalLoc, post.startDate, post.endDate,
                        intermediary.name, post.isKennel))
                .from(application)
                .join(application.post, post)
                .join(application.post.intermediary, intermediary)
                .join(application.post.mainImage, postImage)
                .where(application.status.eq(ApplicationStatus.PROGRESSING)
                        .and(application.volunteer.id.eq(volunteerId)))
                .orderBy(application.modifiedDate.desc())   // 신청 확정 최신순
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    @Override
    public Optional<Application> findByIdAndVolunteerIdWithPost(Long applicationId, Long volunteerId) {
        return Optional.ofNullable(queryFactory
                .select(application)
                .from(application)
                .join(application.post, post).fetchJoin()
                .where(application.id.eq(applicationId)
                        .and(application.volunteer.id.eq(volunteerId)))
                .fetchOne());
    }

    @Override
    public Optional<Application> findByIdAndIntermediaryIdAndStatusWithPost(Long applicationId, Long intermediaryId, ApplicationStatus status) {
        return Optional.ofNullable(queryFactory
                .select(application)
                .from(application)
                .join(application.post, post).fetchJoin()
                .where(application.id.eq(applicationId)
                        .and(application.intermediary.id.eq(intermediaryId))
                        .and(application.status.eq(status)))
                .fetchOne());
    }

    @Override
    public List<ApplicationIntermediaryWaitingResponse> getIntermediaryWaitingApplications(Long intermediaryId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ApplicationIntermediaryWaitingResponse.class,
                        application.createdDate, post.id, postImage.image, dog.name, post.startDate, post.endDate,
                        post.departureLoc, post.arrivalLoc, volunteer.name, application.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.mainImage, postImage)
                .join(application.post.dog, dog)
                .join(application.volunteer, volunteer)
                .where(application.status.eq(ApplicationStatus.WAITING)
                        .and(application.intermediary.id.eq(intermediaryId)))
                .orderBy(application.createdDate.desc())    // 신청 최신순
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    @Override
    public List<ApplicationIntermediaryProgressingResponse> getIntermediaryProgressingApplications(Long intermediaryId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ApplicationIntermediaryProgressingResponse.class,
                        post.id, postImage.image, dog.name, post.startDate, post.endDate,
                        post.departureLoc, post.arrivalLoc, volunteer.name, application.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.mainImage, postImage)
                .join(application.post.dog, dog)
                .join(application.volunteer, volunteer)
                .where(application.status.eq(ApplicationStatus.PROGRESSING)
                        .and(application.intermediary.id.eq(intermediaryId)))
                .orderBy(application.modifiedDate.desc())   // 신청 확정 최신순
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    @Override
    public List<ApplicationVolunteerCompletedResponse> getVolunteerCompletedApplications(Long volunteerId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ApplicationVolunteerCompletedResponse.class,
                        post.id, postImage.image, post.departureLoc, post.arrivalLoc, post.startDate, post.endDate,
                        intermediary.name, post.isKennel, review.id, dogStatus.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.intermediary, intermediary)
                .join(application.post.mainImage, postImage)
                .leftJoin(review).on(post.id.eq(review.post.id))
                .leftJoin(dogStatus).on(post.id.eq(dogStatus.post.id))
                .where(application.status.eq(ApplicationStatus.COMPLETED)
                        .and(application.volunteer.id.eq(volunteerId)))
                .orderBy(application.modifiedDate.desc())   // 신청 봉사완료 최신순
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    @Override
    public List<ApplicationIntermediaryCompletedResponse> getIntermediaryCompletedApplications(Long intermediaryId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ApplicationIntermediaryCompletedResponse.class,
                        post.id, postImage.image, dog.name, post.startDate, post.endDate, post.departureLoc, post.arrivalLoc,
                        volunteer.name, application.id, review.id, dogStatus.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.mainImage, postImage)
                .join(application.post.dog, dog)
                .join(application.volunteer, volunteer)
                .leftJoin(review).on(post.id.eq(review.post.id))
                .leftJoin(dogStatus).on(post.id.eq(dogStatus.post.id))
                .where(application.status.eq(ApplicationStatus.COMPLETED)
                        .and(application.intermediary.id.eq(intermediaryId)))
                .orderBy(application.modifiedDate.desc())   // 신청 봉사완료 최신순
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    // 진행한 이동봉사 건수
    @Override
    public Long getCountOfCompletedApplications(Long id) {
        return queryFactory
                .select(application.count())
                .from(application)
                .where(application.volunteer.id.eq(id)
                        .and(application.status.eq(ApplicationStatus.COMPLETED)))
                .fetchOne();
    }
}
