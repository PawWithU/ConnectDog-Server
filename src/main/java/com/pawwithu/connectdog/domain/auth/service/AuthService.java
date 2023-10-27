package com.pawwithu.connectdog.domain.auth.service;

import com.pawwithu.connectdog.domain.auth.dto.request.VolunteerSignUpRequest;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
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

    private final VolunteerRepository volunteerRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(VolunteerSignUpRequest volunteerSignUpRequest) {

        if (volunteerRepository.existsByEmail(volunteerSignUpRequest.email())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL);
        }
        if (volunteerRepository.existsByNickname(volunteerSignUpRequest.nickname())) {
            throw new BadRequestException(ALREADY_EXIST_NICKNAME);
        }

        Volunteer volunteer = volunteerSignUpRequest.toEntity();
        volunteer.passwordEncode(passwordEncoder);
        volunteerRepository.save(volunteer);
    }

}
