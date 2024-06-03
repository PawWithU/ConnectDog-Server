package com.pawwithu.connectdog.domain.auth.dto.response;

public record IntermediaryEmailResponse(String email) {
    public static IntermediaryEmailResponse of(String email){
        return new IntermediaryEmailResponse(email);
    }
}