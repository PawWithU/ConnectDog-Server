package com.pawwithu.connectdog.domain.oauth.dto.response;

import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;

public interface OAuthInfoResponse {
    SocialType getSocialType();
    String getId();
}
