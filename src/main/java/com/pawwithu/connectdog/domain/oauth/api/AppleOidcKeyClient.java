package com.pawwithu.connectdog.domain.oauth.api;

import com.pawwithu.connectdog.domain.oauth.dto.response.OidcPublicKeyListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AppleOidcKeyClient {

    private final RestTemplate restTemplate;

    public OidcPublicKeyListResponse getAppleOidcOpenKeys() {
        String appleOidcPublicKeyUrl = "https://appleid.apple.com/auth/keys";
        return restTemplate.getForObject(appleOidcPublicKeyUrl, OidcPublicKeyListResponse.class);
    }
}