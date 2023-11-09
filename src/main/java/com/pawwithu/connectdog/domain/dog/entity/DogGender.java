package com.pawwithu.connectdog.domain.dog.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

import static com.pawwithu.connectdog.error.ErrorCode.INVALID_DOG_GENDER;

@Getter
@RequiredArgsConstructor
public enum DogGender {

    MALE("남아"), FEMALE("여아");

    @JsonCreator
    public static DogGender create(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.key.equalsIgnoreCase(requestValue))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(INVALID_DOG_GENDER));
    }

    private final String key;

}