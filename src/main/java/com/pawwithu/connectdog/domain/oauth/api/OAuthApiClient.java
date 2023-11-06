package com.pawwithu.connectdog.domain.oauth.api;

import com.pawwithu.connectdog.domain.oauth.dto.response.OAuthInfoResponse;
import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;

public interface OAuthApiClient {
    SocialType socialType();
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
