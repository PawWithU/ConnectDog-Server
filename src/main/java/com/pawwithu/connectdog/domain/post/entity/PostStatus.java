package com.pawwithu.connectdog.domain.post.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

import static com.pawwithu.connectdog.error.ErrorCode.INVALID_POST_STATUS;

@Getter
@RequiredArgsConstructor
public enum PostStatus {

    RECRUITING("모집중"), WAITING("승인 대기중"), PROGRESSING("진행중"), COMPLETED("봉사 완료"), EXPIRED("마감");

    @JsonCreator
    public static PostStatus create(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.key.equalsIgnoreCase(requestValue))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(INVALID_POST_STATUS));
    }

    private final String key;
}
