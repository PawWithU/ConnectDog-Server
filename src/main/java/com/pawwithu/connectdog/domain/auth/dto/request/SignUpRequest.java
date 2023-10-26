package com.pawwithu.connectdog.domain.auth.dto.request;

import com.pawwithu.connectdog.domain.member.entity.Member;
import com.pawwithu.connectdog.domain.member.entity.Role;

public record SignUpRequest(
        String email, String password, String nickname,
        String name, String phone,
        String url,
        Boolean isOptionAgr,
        Role role
) {
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .name(name)
                .phone(phone)
                .url(url)
                .isOptionAgr(isOptionAgr)
                .role(role)
                .build();
    }
}
