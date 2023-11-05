package com.pawwithu.connectdog.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SocialSignUpRequest(
        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Size(min=2, max=10, message = "2~10자의 닉네임이어야 합니다.")
        String nickName) {
}
