package com.pawwithu.connectdog.domain.auth.dto.response;

public record IntermediaryPhoneResponse(Boolean isDuplicated, String email) {
    public static IntermediaryPhoneResponse of(Boolean isDuplicated, String email){
        return new IntermediaryPhoneResponse(isDuplicated, email);
    }
}
