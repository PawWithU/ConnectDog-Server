package com.pawwithu.connectdog.domain.fcm.dto.request;

import com.pawwithu.connectdog.domain.fcm.entity.VolunteerFcm;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import jakarta.validation.constraints.NotBlank;

public record VolunteerFcmRequest(@NotBlank(message = "fcm 토큰은 필수 입력 값입니다.")
                                  String fcmToken) {

    public static VolunteerFcm volunteerToEntity(Volunteer volunteer, VolunteerFcmRequest request) {
        return VolunteerFcm.builder()
                .volunteer(volunteer)
                .fcmToken(request.fcmToken)
                .build();
    }
}
