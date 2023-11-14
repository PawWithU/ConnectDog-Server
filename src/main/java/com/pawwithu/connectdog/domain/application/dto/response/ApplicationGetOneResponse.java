package com.pawwithu.connectdog.domain.application.dto.response;

import com.pawwithu.connectdog.domain.application.entity.Application;

public record ApplicationGetOneResponse(String volunteerName, String phone, String transportation, String content) {

    public static ApplicationGetOneResponse from(Application application) {
        return new ApplicationGetOneResponse(application.getVolunteerName(), application.getPhone(), application.getTransportation(), application.getContent());
    }

    public ApplicationGetOneResponse from1() {
        return new ApplicationGetOneResponse(volunteerName, phone, transportation, content);
    }
}

