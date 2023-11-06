package com.pawwithu.connectdog.domain.volunteer.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;

import static com.pawwithu.connectdog.error.ErrorCode.UNKNOWN_PROVIDER;

public enum SocialType {
    KAKAO, NAVER;

    @JsonCreator // 이 어노테이션은 Jackson이 JSON에서 객체로 변환할 때 사용
    public static SocialType fromString(String value) {
        for (SocialType type : SocialType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new BadRequestException(UNKNOWN_PROVIDER);
    }
}
