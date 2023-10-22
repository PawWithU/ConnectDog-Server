package com.pawwithu.connectdog.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email; // 이메일
    private String password; // 비밀번호
    private String nickname; // 닉네임
    private String name; // 이름
    private String phone; // 이동봉사자 휴대폰 번호
    private String url; // 이동봉사 단체 링크
    private Boolean isOptionAgr; // 선택 이용약관 체크 여부

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER

    private String socialId; // 로그인한 소셜 타입 식별자 값 (일반 로그인의 경우 null)

    @Builder
    public Member(String email, String password, String nickname, String name, String phone, String url, Boolean isOptionAgr, Role role, SocialType socialType, String socialId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.url = url;
        this.isOptionAgr = isOptionAgr;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }



}
