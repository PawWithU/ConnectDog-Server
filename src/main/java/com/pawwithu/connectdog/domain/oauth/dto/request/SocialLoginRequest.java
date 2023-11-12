package com.pawwithu.connectdog.domain.oauth.dto.request;

import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SocialLoginRequest(
        @NotBlank(message = "AccessToken은 필수 입력 값입니다.")
        String accessToken,
        @NotNull(message = "provider는 필수 입력 값입니다.")
        SocialType provider) {
}
