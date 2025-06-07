package com.pawwithu.connectdog.domain.notification.dto.response;

import com.pawwithu.connectdog.domain.notification.entity.NotificationType;

import java.time.LocalDateTime;

public record NotificationVolunteerQueryResponse(Long id, String notificationType,
                                                 String title, String body, Boolean isRead, Long volunteerId, Long postId, LocalDateTime createdDate) {

    public NotificationVolunteerQueryResponse(Long id, NotificationType notificationType,
                                                 String title, String body, Boolean isRead, Long volunteerId, Long postId, LocalDateTime createdDate) {
        this(id, notificationType.getKey(), title, body, isRead, volunteerId, postId, createdDate);
    }
}