package com.pawwithu.connectdog.domain.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    // 봉사자
    REJECTED("반려 확인"), CONFIRMED("승인 확인"), COMPLETED("봉사 완료"), BADGE("배지 확득"),
    GUIDE("이동봉사 시작하기"),

    // 모집자
    APPLICATION("이동봉사 신청"), CANCELED("이동봉사 신청 취소"), REVIEW_REGISTERED("후기 등록"), EXPIRED("모집 마감"),
    COMPLETED_REQUEST("이동봉사 진행 완료"), BEFORE_EXPIRED("공고 마감");

    private final String key;
}
