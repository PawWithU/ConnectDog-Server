package com.pawwithu.connectdog.domain.application.dto.response;

public record ApplicationVolunteerInfoResponse(String name, String phone) {

    public static ApplicationVolunteerInfoResponse of (String name, String phone) {
        return new ApplicationVolunteerInfoResponse(name, phone);
    }
}
