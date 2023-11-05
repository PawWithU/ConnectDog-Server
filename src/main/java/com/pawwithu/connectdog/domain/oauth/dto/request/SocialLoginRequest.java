package com.pawwithu.connectdog.domain.oauth.dto.request;

import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;
import jakarta.validation.constraints.NotBlank;

public record SocialLoginRequest(
        @NotBlank(message = "AccessToken은 필수 전송 값입니다.")
        String accessToken,
        @NotBlank(message = "provider는 필수 전송 값입니다.")
        SocialType provider) {
}
