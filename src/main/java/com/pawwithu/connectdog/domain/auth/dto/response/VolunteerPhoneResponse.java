package com.pawwithu.connectdog.domain.auth.dto.response;

import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;

public record VolunteerPhoneResponse(Boolean isDuplicated, SocialType socialType, String email) {
    public static VolunteerPhoneResponse of(Boolean isDuplicated, SocialType socialType, String email){
        return new VolunteerPhoneResponse(isDuplicated, socialType, email);
    }
}
