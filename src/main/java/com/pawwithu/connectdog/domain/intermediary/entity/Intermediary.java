package com.pawwithu.connectdog.domain.intermediary.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Intermediary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email; // 이메일
    private String password; // 비밀번호
    @Column(length = 20, nullable = false)
    private String name; // 중개자 이름/중개 단체명
    private String url; // 이동봉사 계정 링크
    private String authImage;   // 인증 사진
    private String profileImage;   // 프로필 사진
    @Column(length = 50)
    private String intro;   // 한줄 소개
    @Enumerated(EnumType.STRING)
    private IntermediaryRole role;  // 권한
    private Boolean isOptionAgr; // 선택 이용약관 체크 여부
    private Boolean notification;   // 알림 true, false

    @Builder
    public Intermediary(String email, String password, String name, String url, String authImage, String profileImage, String intro, IntermediaryRole role, Boolean isOptionAgr, Boolean notification) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.url = url;
        this.authImage = authImage;
        this.profileImage = profileImage;
        this.intro = intro;
        this.role = role;
        this.isOptionAgr = isOptionAgr;
        this.notification = notification;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
