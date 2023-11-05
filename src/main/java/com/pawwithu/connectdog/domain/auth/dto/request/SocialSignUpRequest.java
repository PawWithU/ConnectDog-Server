package com.pawwithu.connectdog.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SocialSignUpRequest(
        @Pattern(regexp = "^[가-힣0-9]*$", message = "닉네임은 한글, 숫자만 사용 가능합니다.")
        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Size(min=2, max=10, message = "2~10자의 닉네임이어야 합니다.")
        String nickname) {
}
