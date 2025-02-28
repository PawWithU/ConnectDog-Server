package com.pawwithu.connectdog.domain.notification.dto.response;

import com.pawwithu.connectdog.domain.notification.entity.NotificationType;

import java.time.format.DateTimeFormatter;

public record NotificationsIntermediaryGetResponse(Long id, String notificationType,
                                                   String title, String body, Boolean isRead, Long intermediaryId, String createdDate) {

    public NotificationsIntermediaryGetResponse(Long id, NotificationType notificationType,
                                                String title, String body, Boolean isRead, Long intermediaryId, String createdDate) {
        this(id, notificationType.getKey(), title, body, isRead, intermediaryId, createdDate);
    }

    public static NotificationsIntermediaryGetResponse of(NotificationsIntermdiaryQueryResponse response) {
        return new NotificationsIntermediaryGetResponse(response.id(), response.notificationType(), response.title(),
                response.body(), response.isRead(), response.intermediaryId(), response.createdDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
