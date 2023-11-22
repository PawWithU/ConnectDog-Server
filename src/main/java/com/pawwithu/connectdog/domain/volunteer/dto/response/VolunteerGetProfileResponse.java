package com.pawwithu.connectdog.domain.volunteer.dto.response;

public record VolunteerGetProfileResponse(Integer profileImageNum, String nickname) {
    public static VolunteerGetProfileResponse of(Integer profileImageNum, String nickname) {
        return new VolunteerGetProfileResponse(profileImageNum, nickname);
    }
}
