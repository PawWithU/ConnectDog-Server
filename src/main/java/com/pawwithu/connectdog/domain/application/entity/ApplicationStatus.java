package com.pawwithu.connectdog.domain.application.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationStatus {

    WAITING("대기중"), PROGRESSING("진행중"), COMPLETED("봉사 완료");

    private final String key;
}
