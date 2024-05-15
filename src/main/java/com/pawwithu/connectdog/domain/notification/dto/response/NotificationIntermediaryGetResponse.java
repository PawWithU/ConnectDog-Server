package com.pawwithu.connectdog.domain.notification.dto.response;

import com.pawwithu.connectdog.domain.notification.entity.NotificationType;

public record NotificationIntermediaryGetResponse(Long id, String image, String notificationType,
                                               String title, String body, Boolean isRead, Long intermediaryId) {

    public NotificationIntermediaryGetResponse(Long id, String image, NotificationType notificationType,
                                            String title, String body, Boolean isRead, Long intermediaryId) {
        this(id, image, notificationType.getKey(), title, body, isRead, intermediaryId);
    }
}
