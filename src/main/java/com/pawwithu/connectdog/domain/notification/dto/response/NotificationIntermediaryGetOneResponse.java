package com.pawwithu.connectdog.domain.notification.dto.response;

import com.pawwithu.connectdog.domain.notification.entity.IntermediaryNotification;

import java.time.format.DateTimeFormatter;

public record NotificationIntermediaryGetOneResponse(Long id, String notificationType,
                                                     String title, String body, Boolean isRead, String createDate) {

    public static NotificationIntermediaryGetOneResponse from (IntermediaryNotification notification) {
        return new NotificationIntermediaryGetOneResponse(notification.getId(), notification.getNotificationType().getKey(),
                notification.getTitle(), notification.getBody(), notification.getIsRead(), notification.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
