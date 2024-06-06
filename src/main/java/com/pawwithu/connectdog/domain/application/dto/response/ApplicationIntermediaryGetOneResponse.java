package com.pawwithu.connectdog.domain.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawwithu.connectdog.domain.application.entity.Application;

import java.time.LocalDate;

public record ApplicationIntermediaryGetOneResponse(Long id,
                                                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
                                                    LocalDate createdDate,
                                                    String nickname,
                                                    String volunteerName, String phone, String content) {

    public static ApplicationIntermediaryGetOneResponse from(Application application) {
        return new ApplicationIntermediaryGetOneResponse(application.getId(), application.getCreatedDate().toLocalDate(), application.getVolunteer().getNickname(),
                application.getVolunteerName(), application.getPhone(), application.getContent());
    }

}
