package com.pawwithu.connectdog.domain.notification.dto.response;

import com.pawwithu.connectdog.domain.notification.entity.NotificationType;

public record NotificationVolunteerGetResponse(Long id, String image, String notificationType,
                                               String title, String body, Boolean isRead, Long volunteerId) {

    public NotificationVolunteerGetResponse(Long id, String image, NotificationType notificationType,
                                            String title, String body, Boolean isRead, Long volunteerId) {
        this(id, image, notificationType.getKey(), title, body, isRead, volunteerId);
    }
}
