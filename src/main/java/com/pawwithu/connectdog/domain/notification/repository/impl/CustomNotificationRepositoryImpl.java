package com.pawwithu.connectdog.domain.notification.repository.impl;

import com.pawwithu.connectdog.domain.notification.dto.response.NotificationVolunteerQueryResponse;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationsIntermdiaryQueryResponse;
import com.pawwithu.connectdog.domain.notification.repository.CustomNotificationRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pawwithu.connectdog.domain.notification.entity.QIntermediaryNotification.intermediaryNotification;
import static com.pawwithu.connectdog.domain.notification.entity.QVolunteerNotification.volunteerNotification;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomNotificationRepositoryImpl implements CustomNotificationRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<NotificationVolunteerQueryResponse> getVolunteerNotifications(Long volunteerId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(NotificationVolunteerQueryResponse.class,
                        volunteerNotification.id, volunteerNotification.notificationType, volunteerNotification.title,
                        volunteerNotification.body, volunteerNotification.isRead, volunteerNotification.volunteer.id,
                        volunteerNotification.postId, volunteerNotification.createdDate))
                .from(volunteerNotification)
                .where(volunteerNotification.volunteer.id.eq(volunteerId))
                .orderBy(volunteerNotification.createdDate.desc())   // 알림 최신순
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    @Override
    public List<NotificationsIntermdiaryQueryResponse> getIntermediaryNotifications(Long intermediaryId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(NotificationsIntermdiaryQueryResponse.class,
                        intermediaryNotification.id, intermediaryNotification.notificationType,
                        intermediaryNotification.title, intermediaryNotification.body, intermediaryNotification.isRead, intermediaryNotification.intermediary.id,
                        intermediaryNotification.createdDate))
                .from(intermediaryNotification)
                .where(intermediaryNotification.intermediary.id.eq(intermediaryId))
                .orderBy(intermediaryNotification.createdDate.desc())   // 알림 최신순
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

}
