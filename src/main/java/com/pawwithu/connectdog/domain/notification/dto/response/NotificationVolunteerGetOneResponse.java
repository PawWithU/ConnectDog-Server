package com.pawwithu.connectdog.domain.notification.dto.response;

import com.pawwithu.connectdog.domain.notification.entity.VolunteerNotification;

import java.time.format.DateTimeFormatter;

public record NotificationVolunteerGetOneResponse(Long id, String notificationType,
                                                  String title, String body, Boolean isRead, Long postId, String createDate) {

    public static NotificationVolunteerGetOneResponse from(VolunteerNotification notification) {
        return new NotificationVolunteerGetOneResponse(notification.getId(), notification.getNotificationType().getKey(),
                notification.getTitle(), notification.getBody(), notification.getIsRead(), notification.getPostId(), notification.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}