package com.pawwithu.connectdog.domain.auth.dto.response;

public record IntermediaryNameResponse(Boolean isDuplicated) {
    public static IntermediaryNameResponse of(Boolean isDuplicated){
        return new IntermediaryNameResponse(isDuplicated);
    }
}

