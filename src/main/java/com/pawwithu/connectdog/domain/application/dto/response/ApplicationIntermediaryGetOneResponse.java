package com.pawwithu.connectdog.domain.application.dto.response;

import com.pawwithu.connectdog.domain.application.entity.Application;

public record ApplicationIntermediaryGetOneResponse(Long id, String volunteerName, String phone, String transportation, String content) {

    public static ApplicationIntermediaryGetOneResponse from(Application application) {
        return new ApplicationIntermediaryGetOneResponse(application.getId(), application.getVolunteerName(),
                application.getPhone(), application.getTransportation(), application.getContent());
    }

}
