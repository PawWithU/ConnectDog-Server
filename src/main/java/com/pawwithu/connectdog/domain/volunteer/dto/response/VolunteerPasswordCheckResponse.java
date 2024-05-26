package com.pawwithu.connectdog.domain.volunteer.dto.response;

public record VolunteerPasswordCheckResponse(Boolean isChecked) {

    public static VolunteerPasswordCheckResponse of(Boolean isChecked) {
        return new VolunteerPasswordCheckResponse(isChecked);
    }
}
