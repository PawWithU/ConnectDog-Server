package com.pawwithu.connectdog.domain.application.dto.response;

import com.pawwithu.connectdog.domain.application.entity.Application;

public record ApplicationVolunteerGetOneResponse(Long id, String volunteerName, String phone, String transportation, String content) {

    public static ApplicationVolunteerGetOneResponse from(Application application) {
        return new ApplicationVolunteerGetOneResponse(application.getId(), application.getVolunteerName(), application.getPhone(), application.getTransportation(), application.getContent());
    }

}

