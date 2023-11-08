package com.pawwithu.connectdog.domain.dog.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DogSize {

    LARGE("대형견"), MEDIUM("중형견"), SMALL("소형견");

    private final String key;

}
