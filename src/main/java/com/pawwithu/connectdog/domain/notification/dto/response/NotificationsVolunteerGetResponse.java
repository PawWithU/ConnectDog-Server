package com.pawwithu.connectdog.domain.notification.dto.response;

import com.pawwithu.connectdog.domain.notification.entity.NotificationType;

import java.time.format.DateTimeFormatter;

public record NotificationsVolunteerGetResponse(Long id, String notificationType,
                                                String title, String body, Boolean isRead, Long volunteerId, String createdDate) {

    public NotificationsVolunteerGetResponse(Long id, NotificationType notificationType,
                                             String title, String body, Boolean isRead, Long volunteerId, String createdDate) {
        this(id, notificationType.getKey(), title, body, isRead, volunteerId, createdDate);
    }

    public static NotificationsVolunteerGetResponse of(NotificationVolunteerQueryResponse response) {
        return new NotificationsVolunteerGetResponse(response.id(), response.notificationType(), response.title(),
                response.body(), response.isRead(), response.volunteerId(), response.createdDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
