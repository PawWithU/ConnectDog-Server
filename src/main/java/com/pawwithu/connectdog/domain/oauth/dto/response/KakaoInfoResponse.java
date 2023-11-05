package com.pawwithu.connectdog.domain.oauth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OAuthInfoResponse {
    @JsonProperty("id")
    private String id; // 소셜 식별 값

    @Override
    public SocialType getSocialType() {
        return SocialType.KAKAO;
    }
    @Override
    public String getId() {
        return id;
    }
}