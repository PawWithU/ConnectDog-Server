package com.pawwithu.connectdog.domain.notification.dto.response;

import com.pawwithu.connectdog.domain.notification.entity.NotificationType;

import java.time.LocalDateTime;

public record NotificationsIntermdiaryQueryResponse(Long id, String notificationType,
                                                    String title, String body, Boolean isRead, Long intermediaryId, LocalDateTime createdDate) {

    public NotificationsIntermdiaryQueryResponse(Long id, NotificationType notificationType,
                                                String title, String body, Boolean isRead, Long intermediaryId, LocalDateTime createdDate) {
        this(id, notificationType.getKey(), title, body, isRead, intermediaryId, createdDate);
    }
}