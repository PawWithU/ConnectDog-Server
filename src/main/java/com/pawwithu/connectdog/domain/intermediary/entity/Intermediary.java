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
    @Column(columnDefinition = "TEXT")
    private String url; // 이동봉사 계정 링크
    @Column(columnDefinition = "TEXT")
    private String authImage;   // 인증 사진
    @Enumerated(EnumType.STRING)
    private IntermediaryRole role;  // 권한
    private Boolean isOptionAgr; // 선택 이용약관 체크 여부

    @Builder
    public Intermediary(String email, String password, String name, String url, String authImage, IntermediaryRole role, Boolean isOptionAgr) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.url = url;
        this.authImage = authImage;
        this.role = role;
        this.isOptionAgr = isOptionAgr;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
