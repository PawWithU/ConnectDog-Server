package com.pawwithu.connectdog.domain.notification.repository;

import com.pawwithu.connectdog.domain.notification.dto.response.NotificationIntermediaryGetResponse;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationVolunteerGetResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomNotificationRepository {

    List<NotificationVolunteerGetResponse> getVolunteerNotifications(Long volunteerId, Pageable pageable);
    List<NotificationIntermediaryGetResponse> getIntermediaryNotifications(Long intermediaryId, Pageable pageable);
}
