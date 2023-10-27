package com.pawwithu.connectdog.domain.auth.dto.request;

import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.entity.VolunteerRole;

public record VolunteerSignUpRequest(String email, String password, String nickname, Boolean isOptionAgr) {

    public Volunteer toEntity() {
        return Volunteer.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .isOptionAgr(isOptionAgr)
                .role(VolunteerRole.USER)
                .build();
    }
}
