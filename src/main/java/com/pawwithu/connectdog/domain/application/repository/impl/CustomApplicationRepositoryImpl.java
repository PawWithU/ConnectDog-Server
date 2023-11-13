package com.pawwithu.connectdog.domain.application.repository.impl;

import com.pawwithu.connectdog.domain.application.dto.response.ApplicationWaitingResponse;
import com.pawwithu.connectdog.domain.application.entity.ApplicationStatus;
import com.pawwithu.connectdog.domain.application.repository.CustomApplicationRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pawwithu.connectdog.domain.application.entity.QApplication.application;
import static com.pawwithu.connectdog.domain.intermediary.entity.QIntermediary.intermediary;
import static com.pawwithu.connectdog.domain.post.entity.QPost.post;
import static com.pawwithu.connectdog.domain.post.entity.QPostImage.postImage;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomApplicationRepositoryImpl implements CustomApplicationRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ApplicationWaitingResponse> getWaitingApplications(Long volunteerId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ApplicationWaitingResponse.class,
                        postImage.image, post.departureLoc, post.arrivalLoc, post.startDate, post.endDate,
                        intermediary.name, post.isKennel, application.id))
                .from(application)
                .join(application.post, post)
                .join(application.post.intermediary, intermediary)
                .join(application.post.mainImage, postImage)
                .where(application.status.eq(ApplicationStatus.WAITING)
                        .and(application.volunteer.id.eq(volunteerId)))
                .orderBy(application.createdDate.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }
}
