package com.pawwithu.connectdog.domain.oauth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class AppleInfoResponse implements OAuthInfoResponse {
    @JsonProperty("id")
    private String id;   // 애플이 제공하는 사용자 고유 ID (sub)

    @Override
    public SocialType getSocialType() {
        return SocialType.APPLE;
    }
    @Override
    public String getId() {
        return id;
    }
}
