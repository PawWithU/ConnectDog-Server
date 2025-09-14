package com.pawwithu.connectdog.domain.post.dto.response;

public record PostCompleteResponse(Boolean isSuccess) {
    public static PostCompleteResponse of(Boolean isSuccess) {
        return new PostCompleteResponse(isSuccess);
    }
}