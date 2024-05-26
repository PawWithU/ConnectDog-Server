package com.pawwithu.connectdog.domain.notification.dto.response;

import com.pawwithu.connectdog.domain.notification.entity.VolunteerNotification;

public record NotificationVolunteerGetOneResponse(Long id, String image, String notificationType,
                                                  String title, String body, Boolean isRead) {

    public static NotificationVolunteerGetOneResponse from(VolunteerNotification notification) {
        return new NotificationVolunteerGetOneResponse(notification.getId(), notification.getImage(), notification.getNotificationType().getKey(),
                notification.getTitle(), notification.getBody(), notification.getIsRead());
    }
}