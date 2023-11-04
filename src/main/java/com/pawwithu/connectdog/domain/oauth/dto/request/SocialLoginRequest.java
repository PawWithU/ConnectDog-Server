package com.pawwithu.connectdog.domain.oauth.dto.request;

import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;

public record SocialLoginRequest(String accessToken, SocialType provider) {
}
