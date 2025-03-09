package com.pawwithu.connectdog.domain.oauth.api;

import com.pawwithu.connectdog.domain.oauth.dto.response.OAuthInfoResponse;
import com.pawwithu.connectdog.domain.oauth.service.AppleIdTokenDecodeService;
import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AppleApiClient implements OAuthApiClient {

    private final AppleIdTokenDecodeService appleIdTokenDecodeService;

    private String apiUrl = "https://kapi.kakao.com";
    private final RestTemplate restTemplate;

    @Override
    public SocialType socialType() {
        return SocialType.APPLE;
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String socialToken) {
        return appleIdTokenDecodeService.getPayloadFromIdToken(socialToken);
    }
}
