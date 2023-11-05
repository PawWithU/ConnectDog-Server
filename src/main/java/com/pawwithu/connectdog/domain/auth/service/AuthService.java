package com.pawwithu.connectdog.domain.auth.service;

import com.pawwithu.connectdog.common.s3.FileService;
import com.pawwithu.connectdog.domain.auth.dto.request.IntermediarySignUpRequest;
import com.pawwithu.connectdog.domain.auth.dto.request.SocialSignUpRequest;
import com.pawwithu.connectdog.domain.auth.dto.request.VolunteerSignUpRequest;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.oauth.dto.response.LoginResponse;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.entity.VolunteerRole;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import com.pawwithu.connectdog.error.exception.custom.TokenException;
import com.pawwithu.connectdog.jwt.service.JwtService;
import com.pawwithu.connectdog.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.pawwithu.connectdog.error.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final VolunteerRepository volunteerRepository;
    private final IntermediaryRepository intermediaryRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final JwtService jwtService;
    private final RedisUtil redisUtil;

    public void volunteerSignUp(VolunteerSignUpRequest request) {

        if (volunteerRepository.existsByEmail(request.email())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL);
        }
        if (intermediaryRepository.existsByEmail(request.email())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL);
        }
        if (volunteerRepository.existsByNickname(request.nickname())) {
            throw new BadRequestException(ALREADY_EXIST_NICKNAME);
        }

        Volunteer volunteer = request.toEntity();
        volunteer.passwordEncode(passwordEncoder);
        volunteerRepository.save(volunteer);
    }

    public void intermediarySignUp(IntermediarySignUpRequest request, MultipartFile authFile, MultipartFile profileFile) {

        if (intermediaryRepository.existsByEmail(request.email())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL);
        }
        if (volunteerRepository.existsByEmail(request.email())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL);
        }
        String authImage = fileService.uploadFile(authFile, "intermediary/authImage");
        if (authImage == null) {
            throw new BadRequestException(FILE_NOT_FOUND);
        }
        String profileImage = fileService.uploadFile(profileFile, "intermediary/profileImage");
        Intermediary intermediary = IntermediarySignUpRequest.toEntity(request, authImage, profileImage);
        intermediary.passwordEncode(passwordEncoder);
        intermediaryRepository.save(intermediary);
    }

    public LoginResponse volunteerSocialSignUp(SocialSignUpRequest socialSignUpRequest, HttpServletRequest request) {

        if (volunteerRepository.existsByNickname(socialSignUpRequest.nickname())) {
            throw new BadRequestException(ALREADY_EXIST_NICKNAME);
        }

        // AccessToken 으로 이동봉사자 찾기
        String accessToken = jwtService.extractAccessToken(request).orElseThrow(() -> new TokenException(INVALID_TOKEN));
        Long id = jwtService.extractId(accessToken).orElseThrow(() -> new TokenException(INVALID_TOKEN));
        String roleName = jwtService.extractRoleName(accessToken).orElseThrow(() -> new TokenException(INVALID_TOKEN));
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));

        // 추가 정보 업데이트
        String nickname = socialSignUpRequest.nickname();
        volunteer.updateSocialVolunteer(nickname, VolunteerRole.VOLUNTEER); // GUEST -> VOLUNTEER

        // 토큰 재발행
        String refreshToken = redisUtil.get(roleName, id);
        return jwtService.reIssueToken(refreshToken);
    }
}
