package com.pawwithu.connectdog.domain.oauth.dto.response;

public record SocialLoginResponse(String roleName, String accessToken, String refreshToken) {
    public static SocialLoginResponse of(String roleName, String accessToken, String refreshToken){
        return new SocialLoginResponse(roleName, accessToken, refreshToken);
    }
}
