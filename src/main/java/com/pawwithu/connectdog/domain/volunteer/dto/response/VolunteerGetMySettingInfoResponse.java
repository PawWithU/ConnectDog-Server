package com.pawwithu.connectdog.domain.volunteer.dto.response;

import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;

public record VolunteerGetMySettingInfoResponse(String name, String phone, SocialType socialType, String email) {
    public static VolunteerGetMySettingInfoResponse of(String name, String phone, SocialType socialType, String email){
        return new VolunteerGetMySettingInfoResponse(name, phone, socialType, email);
    }
}
