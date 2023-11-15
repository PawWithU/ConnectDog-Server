package com.pawwithu.connectdog.domain.dogStatus.repository.impl;

import com.pawwithu.connectdog.domain.dogStatus.dto.response.DogStatusGetOneResponse;
import com.pawwithu.connectdog.domain.dogStatus.repository.CustomDogStatusRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pawwithu.connectdog.domain.application.entity.QApplication.application;
import static com.pawwithu.connectdog.domain.dog.entity.QDog.dog;
import static com.pawwithu.connectdog.domain.dogStatus.entity.QDogStatusImage.dogStatusImage;
import static com.pawwithu.connectdog.domain.dogStatus.entity.QDogStatus.dogStatus;
import static com.pawwithu.connectdog.domain.intermediary.entity.QIntermediary.intermediary;
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
    public DogStatusGetOneResponse getOneDogStatus(Long id, Long dogStatusId) {

        return queryFactory
                .select(Projections.constructor(DogStatusGetOneResponse.class,
                        dog.name,
                        JPAExpressions.select(volunteer.nickname)
                                .from(application)
                                .where(application.post.id.eq(dogStatus.post.id))
                        , dogStatusImage.image,
                        post.startDate, post.endDate, post.departureLoc, post.arrivalLoc,
                        dogStatus.content))
                .from(dogStatus)
                .join(dogStatus.intermediary, intermediary)
                .join(dogStatus.mainImage, dogStatusImage)
                .join(dogStatus.post, post)
                .where(dogStatus.id.eq(dogStatusId))
                .fetchOne();

    }
}
