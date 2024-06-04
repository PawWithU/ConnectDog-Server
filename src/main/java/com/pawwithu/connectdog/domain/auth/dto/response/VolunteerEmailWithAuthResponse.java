package com.pawwithu.connectdog.domain.auth.dto.response;

public record VolunteerEmailWithAuthResponse(String authCode, String accessToken) {
    public static VolunteerEmailWithAuthResponse of(String authCode, String accessToken){
        return new VolunteerEmailWithAuthResponse(authCode, accessToken);
    }
}