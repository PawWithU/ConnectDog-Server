package com.pawwithu.connectdog.domain.notification.repository;

import com.pawwithu.connectdog.domain.notification.dto.response.NotificationsIntermdiaryQueryResponse;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationsVolunteerGetResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomNotificationRepository {

    List<NotificationsVolunteerGetResponse> getVolunteerNotifications(Long volunteerId, Pageable pageable);
    List<NotificationsIntermdiaryQueryResponse> getIntermediaryNotifications(Long intermediaryId, Pageable pageable);
}
