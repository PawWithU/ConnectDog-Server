package com.pawwithu.connectdog.domain.intermediary.dto.response;

public record IntermediaryPasswordCheckResponse(Boolean isChecked) {

    public static IntermediaryPasswordCheckResponse of(Boolean isChecked) {
        return new IntermediaryPasswordCheckResponse(isChecked);
    }
}
