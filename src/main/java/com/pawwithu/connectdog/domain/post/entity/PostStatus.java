package com.pawwithu.connectdog.domain.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostStatus {

    RECRUITING("모집중"), WAITING("승인 대기중"), PROGRESSING("진행중"), COMPLETED("봉사 완료");

    private final String key;
}
