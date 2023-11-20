package com.pawwithu.connectdog.domain.fcm.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FcmTokenRequest(@NotBlank(message = "fcm 토큰은 필수 입력 값입니다.")
                              String fcmToken) {
}
