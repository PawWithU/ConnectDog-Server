package com.pawwithu.connectdog.domain.volunteer.dto.response;

public record VolunteerGetNotificationResponse(Boolean notification) {

    public static VolunteerGetNotificationResponse of(Boolean notification) {
        return new VolunteerGetNotificationResponse(notification);
    }
}
