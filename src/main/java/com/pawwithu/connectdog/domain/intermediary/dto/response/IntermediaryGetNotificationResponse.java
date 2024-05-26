package com.pawwithu.connectdog.domain.intermediary.dto.response;

public record IntermediaryGetNotificationResponse(Boolean notification) {

    public static IntermediaryGetNotificationResponse of(Boolean notification) {
        return new IntermediaryGetNotificationResponse(notification);
    }
}
