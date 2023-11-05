package com.pawwithu.connectdog.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(@NotBlank(message = "이메일은 필수 전송 값입니다.")
                                  String refreshToken) {
}
