package com.pawwithu.connectdog.domain.fcm.dto.request;

import com.pawwithu.connectdog.domain.fcm.entity.IntermediaryFcm;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import jakarta.validation.constraints.NotBlank;

public record IntermediaryFcmRequest(@NotBlank(message = "fcm 토큰은 필수 입력 값입니다.")
                                     String fcmToken) {

    public static IntermediaryFcm IntermediaryToEntity(Intermediary intermediary, IntermediaryFcmRequest request) {
        return IntermediaryFcm.builder()
                .intermediary(intermediary)
                .fcmToken(request.fcmToken)
                .build();
    }
}