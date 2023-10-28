package com.pawwithu.connectdog.domain.volunteer.dto.response;

public record NicknameResponse(Boolean isDuplicated) {

    public static NicknameResponse of(Boolean isDuplicated){
        return new NicknameResponse(isDuplicated);
    }
}
