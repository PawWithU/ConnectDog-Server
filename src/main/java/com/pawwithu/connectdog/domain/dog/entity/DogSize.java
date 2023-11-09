package com.pawwithu.connectdog.domain.dog.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

import static com.pawwithu.connectdog.error.ErrorCode.INVALID_DOG_SIZE;

@Getter
@RequiredArgsConstructor
public enum DogSize {

    SMALL("소형견"), MEDIUM("중형견"), LARGE("대형견");

    @JsonCreator
    public static DogSize create(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.key.equalsIgnoreCase(requestValue))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(INVALID_DOG_SIZE));
    }

    private final String key;

}
