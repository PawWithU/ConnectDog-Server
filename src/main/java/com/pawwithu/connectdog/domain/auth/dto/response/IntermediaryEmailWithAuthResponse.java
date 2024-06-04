package com.pawwithu.connectdog.domain.auth.dto.response;

public record IntermediaryEmailWithAuthResponse(String authCode, String accessToken) {
    public static IntermediaryEmailWithAuthResponse of(String authCode, String accessToken){
        return new IntermediaryEmailWithAuthResponse(authCode, accessToken);
    }
}