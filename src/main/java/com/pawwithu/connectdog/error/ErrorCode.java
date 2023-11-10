package com.pawwithu.connectdog.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ALREADY_EXIST_EMAIL("A1", "이미 등록된 이메일입니다."),
    ALREADY_EXIST_NICKNAME("A2", "이미 사용 중인 닉네임입니다."),
    ALREADY_LOGOUT_MEMBER("A3", "이미 로그아웃한 회원입니다"),
    EMAIL_SEND_ERROR("A4", "이메일 인증 코드 전송을 실패했습니다."),
    UNKNOWN_PROVIDER("A4", "provider 값이 KAKAO 또는 NAVER가 아닙니다."),


    VOLUNTEER_NOT_FOUND("M1", "해당 이동봉사자를 찾을 수 없습니다."), // Member -> M (이동봉사자, 이동봉사 중개 통일)
    INTERMEDIARY_NOT_FOUND("M2", "해당 이동봉사 중개를 찾을 수 없습니다."),
    INVALID_ROLE_NAME("M3", "해당 ROLE_NAME을 가진 이동봉사자/중개를 찾을 수 없습니다."),

    TOKEN_NOT_EXIST("T1", "토큰이 존재하지 않습니다."),
    TOKEN_IS_EXPIRED("T2", "만료된 토큰입니다."),
    INVALID_TOKEN("T3", "잘못된 토큰입니다."),
    TOKEN_NOT_CREATED("T4", "토큰 생성에 실패했습니다."),
    TOKEN_NOT_MATCHED("T5", "해당 RefreshToken을 Redis에서 찾을 수 없습니다."),

    FILE_NOT_FOUND("F1", "파일이 존재하지 않습니다."),
    INVALID_FILE_UPLOAD("F2", "파일 업로드에 실패했습니다."),

    INVALID_DOG_SIZE("D1", "잘못된 강아지 사이즈입니다."),
    INVALID_DOG_GENDER("D2", "잘못된 강아지 성별입니다."),

    INVALID_POST_STATUS("P1", "잘못된 공고 상태입니다."),
    POST_NOT_FOUND("P2", "해당 공고를 찾을 수 없습니다.");


    private final String code;
    private final String message;

}
