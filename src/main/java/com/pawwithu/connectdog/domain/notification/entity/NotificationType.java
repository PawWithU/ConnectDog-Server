package com.pawwithu.connectdog.domain.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    // 봉사자
    REJECTED("반려 확인"), CONFIRMED("승인 확인"), COMPLETED("봉사 완료"), BADGE("배지 확득"),

    // 모집자
    APPLICATION("신청 확인"), CANCELED("봉사 취소"), REVIEW_REGISTERED("후기 확인"), EXPIRED("모집 마감"),
    COMPLETED_REQUEST("이동봉사 진행 완료"), BEFORE_EXPIRED("공고 마감");

    private final String key;
}
