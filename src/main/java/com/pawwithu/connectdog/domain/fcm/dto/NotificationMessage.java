package com.pawwithu.connectdog.domain.fcm.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationMessage {
    // 봉사
    GUIDE("이동봉사 시작하기", "이동봉사, 어떻게 하는 건가요?\n코넥독이 이동봉사 가이드를 준비했어요!"),
    CONFIRM("이동봉사 승인", "이동봉사가 승인되었어요🎉\n모집자의 연락을 기다려 주세요!"),
    REJECT("이동봉사 반려", "이동봉사가 반려되었어요😥\n다른 이동봉사를 찾아볼까요?"),
    COMPLETED("이동봉사 완료", "이동봉사 진행이 완료되었어요🐾\n소중한 후기를 들려주세요!"),

    // 모집자
    APPLICATION("이동봉사 신청", "님이 이동봉사를 신청하셨어요.\n지금 확인해 보세요!"),
    CANCELED("이동봉사 신청 취소", "님이 이동봉사를 취소하셨어요.\n해당 공고는 모집중 상태로 변경됩니다."),
    REVIEW_REGISTERED("이동봉사 후기 등록", "봉사 후기가 등록되었습니다.\n지금 확인해 보세요!"),
    EXPIRED("이동봉사 모집 기간 만료", "모집 기간 만료로 공고가 마감되었습니다.\n아직 봉사자를 구하지 못했다면 기간을 조정해 보세요!"),
    COMPLETED_REQUEST("이동봉사 진행 완료", "이동봉사 진행이 완료되었나요?\n봉사 완료 버튼을 눌러주세요!");

    private final String title;
    private final String body;

    public String getTitleWithLoc(String departureLoc, String arrivalLoc) {
        return departureLoc + "→" + arrivalLoc;
    }

    public String getBodyWithName(String nickname) {
        return nickname + body;
    }

}