package com.pawwithu.connectdog.domain.application.dto.response;

public record ApplicationSuccessResponse(Boolean isSuccess) {
    public static ApplicationSuccessResponse of(Boolean isSuccess) {
        return new ApplicationSuccessResponse(isSuccess);
    }
}
