package com.pawwithu.connectdog.domain.volunteer.service;

import com.pawwithu.connectdog.domain.volunteer.dto.request.AdditionalAuthRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.NicknameRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.response.NicknameResponse;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.ErrorCode;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    @Transactional(readOnly = true)
    public NicknameResponse isNicknameDuplicated(NicknameRequest nickNameRequest) {
        Boolean isDuplicated = volunteerRepository.existsByNickname(nickNameRequest.nickname());
        NicknameResponse response = NicknameResponse.of(isDuplicated);
        return response;
    }

    public void additionalAuth(String email, AdditionalAuthRequest request) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(ErrorCode.VOLUNTEER_NOT_FOUND));
        volunteer.updateNameAndPhone(request.name(), request.phone());
    }
}
