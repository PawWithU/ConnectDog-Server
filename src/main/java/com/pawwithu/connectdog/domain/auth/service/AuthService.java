package com.pawwithu.connectdog.domain.auth.service;

import com.pawwithu.connectdog.domain.auth.dto.request.SignUpRequest;
import com.pawwithu.connectdog.domain.member.entity.Member;
import com.pawwithu.connectdog.domain.member.repository.MemberRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pawwithu.connectdog.error.ErrorCode.ALREADY_EXIST_EMAIL;
import static com.pawwithu.connectdog.error.ErrorCode.ALREADY_EXIST_NICKNAME;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest signUpRequest) {

        if (memberRepository.existsByEmail(signUpRequest.email())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL);
        }
        if (memberRepository.existsByNickname(signUpRequest.nickname())) {
            throw new BadRequestException(ALREADY_EXIST_NICKNAME);
        }

        Member member = signUpRequest.toEntity();
        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);
    }

}
