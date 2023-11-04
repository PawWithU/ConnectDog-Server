package com.pawwithu.connectdog.domain.oauth.api;

import com.pawwithu.connectdog.domain.oauth.dto.response.KakaoInfoResponse;
import com.pawwithu.connectdog.domain.oauth.dto.response.OAuthInfoResponse;
import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {
    private String apiUrl = "https://kapi.kakao.com";
    private final RestTemplate restTemplate;

    @Override
    public SocialType socialType() {
        return SocialType.KAKAO;
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"id\"]"); // id 값만 받아옴

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
    }
}