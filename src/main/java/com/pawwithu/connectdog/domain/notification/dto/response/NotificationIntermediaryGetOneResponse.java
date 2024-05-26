package com.pawwithu.connectdog.domain.notification.dto.response;

import com.pawwithu.connectdog.domain.notification.entity.IntermediaryNotification;

public record NotificationIntermediaryGetOneResponse(Long id, String image, String notificationType,
                                                     String title, String body, Boolean isRead) {

    public static NotificationIntermediaryGetOneResponse from (IntermediaryNotification notification) {
        return new NotificationIntermediaryGetOneResponse(notification.getId(), notification.getImage(), notification.getNotificationType().getKey(),
                notification.getTitle(), notification.getBody(), notification.getIsRead());
    }
}
