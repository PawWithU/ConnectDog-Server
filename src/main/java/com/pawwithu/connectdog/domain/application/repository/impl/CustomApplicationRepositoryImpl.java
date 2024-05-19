package com.pawwithu.connectdog.domain.application.repository.impl;

import com.pawwithu.connectdog.domain.application.dto.response.*;
import com.pawwithu.connectdog.domain.application.entity.Application;
import com.pawwithu.connectdog.domain.application.entity.ApplicationStatus;
import com.pawwithu.connectdog.domain.application.repository.CustomApplicationRepository;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    private final EntityManager em;

    @Override
    public List<ApplicationVolunteerWaitingResponse> getVolunteerWaitingApplications(Long volunteerId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ApplicationVolunteerWaitingResponse.class,
                        post.id, postImage.image, dog.name, post.departureLoc, post.arrivalLoc,
                        post.startDate, post.endDate, post.pickUpTime,
                        dog.size, post.isKennel, application.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.mainImage, postImage)
                .join(application.post.dog, dog)
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
                        post.id, postImage.image, dog.name, post.departureLoc, post.arrivalLoc,
                        post.startDate, post.endDate, post.pickUpTime,
                        dog.size, post.isKennel, application.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.mainImage, postImage)
                .join(application.post.dog, dog)
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
                        application.createdDate, post.id, postImage.image, dog.name,
                        post.departureLoc, post.arrivalLoc, post.startDate, post.endDate, post.pickUpTime,
                        dog.size, post.isKennel, application.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.mainImage, postImage)
                .join(application.post.dog, dog)
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
                        post.id, postImage.image, dog.name, post.departureLoc, post.arrivalLoc,
                        post.startDate, post.endDate, post.pickUpTime,
                        dog.size, post.isKennel, application.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.mainImage, postImage)
                .join(application.post.dog, dog)
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
                        post.id, postImage.image, dog.name, post.departureLoc, post.arrivalLoc,
                        post.startDate, post.endDate, post.pickUpTime,
                        dog.size, post.isKennel, review.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.mainImage, postImage)
                .join(application.post.dog, dog)
                .leftJoin(review).on(post.id.eq(review.post.id))
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
                        post.id, postImage.image, dog.name, post.departureLoc, post.arrivalLoc,
                        post.startDate, post.endDate, post.pickUpTime,
                        dog.size, post.isKennel, review.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.mainImage, postImage)
                .join(application.post.dog, dog)
                .leftJoin(review).on(post.id.eq(review.post.id))
                .leftJoin(dogStatus).on(post.id.eq(dogStatus.post.id))
                .where(application.status.eq(ApplicationStatus.COMPLETED)
                        .and(application.intermediary.id.eq(intermediaryId)))
                .orderBy(application.modifiedDate.desc())   // 신청 봉사완료 최신순
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    @Override
    public List<Tuple> getCountOfApplicationsByStatus(Long id) {
        return queryFactory
                .select(application.status, application.count())
                .from(application)
                .where(application.volunteer.id.eq(id))
                .groupBy(application.status)
                .fetch();
    }

    @Override
    public boolean existsByPostIdAndPostStatus(Long postId) {
        return queryFactory
                .select(application)
                .from(application)
                .where(application.post.id.eq(postId)
                        .and(application.status.ne(ApplicationStatus.REJECTED)))
                .fetchOne() != null;
    }

    // 어제 모집 마감된 신청 가져오기
    @Override
    public List<Application> getYesterdayExpiredApplications(LocalDate date) {
        return queryFactory.selectFrom(application)
                .where(application.status.eq(ApplicationStatus.REJECTED)
                        .and(application.post.endDate.eq(date)))
                .fetch();
    }

    // 모집 마감 시 신청 자동 반려
    @Override
    public void updateExpiredApplications(LocalDate date) {
        queryFactory.update(application)
                .set(application.status, ApplicationStatus.REJECTED)
                .where(application.status.eq(ApplicationStatus.WAITING)
                        .and(application.post.endDate.before(date)))
                .execute();

        em.flush();
        em.clear();
    }

    // 어제 일정이 종료된 진행중인 봉사 신청 가져오기
    @Override
    public List<Application> getExpiredProgressingPosts(LocalDate date) {
        return queryFactory.selectFrom(application)
                .join(application.post, post)
                .where(application.status.eq(ApplicationStatus.PROGRESSING)
                        .and(post.endDate.eq(date)))
                .fetch();
    }

}
