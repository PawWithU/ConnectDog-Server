package com.pawwithu.connectdog.domain.dogStatus.repository.impl;

import com.pawwithu.connectdog.domain.dogStatus.dto.response.DogStatusGetOneResponse;
import com.pawwithu.connectdog.domain.dogStatus.repository.CustomDogStatusRepository;
import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetDogStatusesResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pawwithu.connectdog.domain.application.entity.QApplication.application;
import static com.pawwithu.connectdog.domain.dog.entity.QDog.dog;
import static com.pawwithu.connectdog.domain.dogStatus.entity.QDogStatusImage.dogStatusImage;
import static com.pawwithu.connectdog.domain.dogStatus.entity.QDogStatus.dogStatus;
import static com.pawwithu.connectdog.domain.post.entity.QPost.post;
import static com.pawwithu.connectdog.domain.volunteer.entity.QVolunteer.volunteer;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomDogStatusRepositoryImpl implements CustomDogStatusRepository {
    private final JPAQueryFactory queryFactory;

    // 대표 이미지를 제외한 근황 이미지 조회
    @Override
    public List<String> getOneDogStatusImages(Long dogStatusId) {
        return queryFactory
                .select(dogStatusImage.image)
                .from(dogStatusImage)
                .join(dogStatusImage.dogStatus, dogStatus)
                .where(dogStatusImage.dogStatus.id.eq(dogStatusId)
                        .and(dogStatus.mainImage.id.ne(dogStatusImage.id)))
                .fetch();
    }

    // 근황 단건 조회 (대표 이미지를 제외한 다른 이미지 포함 X)
    @Override
    public DogStatusGetOneResponse getOneDogStatus(Long dogStatusId) {
        return queryFactory
                .select(Projections.constructor(DogStatusGetOneResponse.class,
                        dog.name, volunteer.nickname, dogStatusImage.image,
                        post.startDate, post.endDate, post.departureLoc, post.arrivalLoc,
                        dogStatus.content))
                .from(dogStatus)
                .join(dogStatus.mainImage, dogStatusImage)
                .join(dogStatus.post, post)
                .join(post.dog, dog)
                .join(application).on(application.post.id.eq(dogStatus.post.id))
                .join(application.volunteer, volunteer)
                .where(dogStatus.id.eq(dogStatusId))
                .fetchOne();
    }

    // 이동봉사 중개 별 근황 조회 (최신순)
    @Override
    public List<IntermediaryGetDogStatusesResponse> getIntermediaryDogStatuses(Long intermediaryId, Pageable pageable) {
        List<IntermediaryGetDogStatusesResponse> dogStatuses = queryFactory
                .select(Projections.constructor(IntermediaryGetDogStatusesResponse.class,
                        dog.name, volunteer.nickname, dogStatusImage.image,
                        post.startDate, post.endDate, post.departureLoc, post.arrivalLoc,
                        dogStatus.content))
                .from(dogStatus)
                .where(dogStatus.post.intermediary.id.eq(intermediaryId))
                .join(dogStatus.mainImage, dogStatusImage)
                .join(dogStatus.post, post)
                .join(post.dog, dog)
                .join(application).on(application.post.id.eq(dogStatus.post.id))
                .join(application.volunteer, volunteer)
                .orderBy(dogStatus.createdDate.desc())
                .offset(pageable.getOffset()) // 페이지 번호
                .limit(pageable.getPageSize()) // 페이지 사이즈
                .fetch();

        return dogStatuses;
    }

    // 남긴 근황 총 건수
    @Override
    public Long getCountOfDogStatuses(Long intermediaryId) {
        return queryFactory
                .select(dogStatus.count())
                .from(dogStatus)
                .where(dogStatus.intermediary.id.eq(intermediaryId))
                .fetchOne();
    }
}
