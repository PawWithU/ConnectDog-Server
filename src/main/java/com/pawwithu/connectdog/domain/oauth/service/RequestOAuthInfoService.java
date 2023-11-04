package com.pawwithu.connectdog.domain.oauth.service;

import com.pawwithu.connectdog.domain.oauth.api.OAuthApiClient;
import com.pawwithu.connectdog.domain.oauth.dto.request.SocialLoginRequest;
import com.pawwithu.connectdog.domain.oauth.dto.response.OAuthInfoResponse;
import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<SocialType, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::socialType, Function.identity())
        );
    }

    public OAuthInfoResponse request(SocialLoginRequest request) {
        OAuthApiClient client = clients.get(request.provider());
        String accessToken = request.accessToken();
        return client.requestOauthInfo(accessToken);
    }
}
