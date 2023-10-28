package com.pawwithu.connectdog.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ALREADY_EXIST_EMAIL("A1", "이미 존재하는 이메일입니다."),
    ALREADY_EXIST_NICKNAME("A2", "이미 존재하는 사용자 닉네임입니다."),
    ALREADY_LOGOUT_MEMBER("A3", "이미 로그아웃한 회원입니다"),
    EMAIL_SEND_ERROR("A4", "이메일 인증 코드 전송을 실패했습니다."),

    TOKEN_NOT_EXIST("T1", "토큰이 존재하지 않습니다."),
    TOKEN_IS_EXPIRED("T2", "만료된 토큰입니다."),
    INVALID_TOKEN("T3", "잘못된 토큰입니다.");


    private final String code;
    private final String message;

}
