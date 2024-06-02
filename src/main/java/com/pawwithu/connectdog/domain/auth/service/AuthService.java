package com.pawwithu.connectdog.domain.auth.service;

import com.pawwithu.connectdog.common.s3.FileService;
import com.pawwithu.connectdog.domain.application.entity.Application;
import com.pawwithu.connectdog.domain.application.repository.ApplicationRepository;
import com.pawwithu.connectdog.domain.auth.dto.request.*;
import com.pawwithu.connectdog.domain.auth.dto.response.IntermediaryPhoneResponse;
import com.pawwithu.connectdog.domain.auth.dto.response.VolunteerPhoneResponse;
import com.pawwithu.connectdog.domain.badge.repository.VolunteerBadgeRepository;
import com.pawwithu.connectdog.domain.bookmark.repository.BookmarkRepository;
import com.pawwithu.connectdog.domain.fcm.repository.IntermediaryFcmRepository;
import com.pawwithu.connectdog.domain.fcm.repository.VolunteerFcmRepository;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.review.entity.Review;
import com.pawwithu.connectdog.domain.review.repository.ReviewRepository;
import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.entity.VolunteerRole;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import com.pawwithu.connectdog.jwt.service.JwtService;
import com.pawwithu.connectdog.util.RedisUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.pawwithu.connectdog.error.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final EntityManager entityManager;

    private final VolunteerRepository volunteerRepository;
    private final IntermediaryRepository intermediaryRepository;
    private final ReviewRepository reviewRepository;
    private final ApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final JwtService jwtService;
    private final RedisUtil redisUtil;
    private final VolunteerFcmRepository volunteerFcmRepository;
    private final IntermediaryFcmRepository intermediaryFcmRepository;
    private final BookmarkRepository bookmarkRepository;
    private final VolunteerBadgeRepository volunteerBadgeRepository;

    public void volunteerSignUp(VolunteerSignUpRequest request) {

        if (volunteerRepository.existsByPhone(request.phone())) {
            throw new BadRequestException(ALREADY_EXIST_PHONE);
        }
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

        if (intermediaryRepository.existsByPhone(request.phone())) {
            throw new BadRequestException(ALREADY_EXIST_PHONE);
        }
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

    public void volunteerSocialSignUp(String email, SocialSignUpRequest socialSignUpRequest) {

        if (volunteerRepository.existsByPhone(socialSignUpRequest.phone())) {
            throw new BadRequestException(ALREADY_EXIST_PHONE);
        }
        if (volunteerRepository.existsByNickname(socialSignUpRequest.nickname())) {
            throw new BadRequestException(ALREADY_EXIST_NICKNAME);
        }

        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));

        // 추가 정보 업데이트
        String name = socialSignUpRequest.name();
        String phone = socialSignUpRequest.phone();
        String nickname = socialSignUpRequest.nickname();
        Integer profileImageNum = socialSignUpRequest.profileImageNum();
        Boolean isOptionAgr = socialSignUpRequest.isOptionAgr();
        volunteer.updateSocialVolunteer(name, phone, nickname, VolunteerRole.VOLUNTEER, profileImageNum, isOptionAgr); // GUEST -> VOLUNTEER
    }

    public void volunteersLogout(HttpServletRequest request, String email) {
        String accessToken = jwtService.extractAccessToken(request).orElseThrow(() -> new BadRequestException(TOKEN_NOT_EXIST));
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        String roleName = jwtService.extractRoleName(accessToken).orElseThrow(() -> new BadRequestException(NOT_FOUND_ROLE_NAME));

        redisUtil.delete(roleName, volunteer.getId());
        volunteerFcmRepository.deleteByVolunteerId(volunteer.getId());
        redisUtil.setBlackList(accessToken, "accessToken", jwtService.getAccessTokenExpirationPeriod());
    }

    public void intermediariesLogout(HttpServletRequest request, String email) {
        String accessToken = jwtService.extractAccessToken(request).orElseThrow(() -> new BadRequestException(TOKEN_NOT_EXIST));
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        String roleName = jwtService.extractRoleName(accessToken).orElseThrow(() -> new BadRequestException(NOT_FOUND_ROLE_NAME));

        redisUtil.delete(roleName, intermediary.getId());
        intermediaryFcmRepository.deleteByIntermediaryId(intermediary.getId());
        redisUtil.setBlackList(accessToken, "accessToken", jwtService.getAccessTokenExpirationPeriod());
    }

    @Transactional(readOnly = true)
    public VolunteerPhoneResponse isVolunteerPhoneDuplicated(VolunteerPhoneRequest request) {
        Boolean isDuplicated = volunteerRepository.existsByPhone(request.phone());
        String email = null;
        SocialType socialType = null;

        if (isDuplicated) {
            Volunteer volunteer = volunteerRepository.findByPhone(request.phone()).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
            socialType = volunteer.getSocialType();
            email = volunteer.getEmail();
        }

        VolunteerPhoneResponse response = VolunteerPhoneResponse.of(isDuplicated, socialType, email);
        return response;
    }

    @Transactional(readOnly = true)
    public IntermediaryPhoneResponse isIntermediaryPhoneDuplicated(IntermediaryPhoneRequest request) {
        Boolean isDuplicated = intermediaryRepository.existsByPhone(request.phone());
        String email = null;

        if (isDuplicated) {
            Intermediary intermediary = intermediaryRepository.findByPhone(request.phone()).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
            email = intermediary.getEmail();
        }

        IntermediaryPhoneResponse response = IntermediaryPhoneResponse.of(isDuplicated, email);
        return response;
    }

    public void volunteersWithdraw(HttpServletRequest request, String email) {
        String accessToken = jwtService.extractAccessToken(request).orElseThrow(() -> new BadRequestException(TOKEN_NOT_EXIST));
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        String roleName = jwtService.extractRoleName(accessToken).orElseThrow(() -> new BadRequestException(NOT_FOUND_ROLE_NAME));

        try {
            redisUtil.delete(roleName, volunteer.getId());
            volunteerFcmRepository.deleteByVolunteerId(volunteer.getId());
            redisUtil.setBlackList(accessToken, "accessToken", jwtService.getAccessTokenExpirationPeriod());

            Volunteer deletedVolunteer = volunteerRepository.findByEmail("deleted@connectdog.com").orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
            List<Review> reviews = reviewRepository.findByVolunteer(volunteer);
            for (Review review : reviews) {
                review.updateDeletedVolunteer(deletedVolunteer);
            }

            List<Application> applications = applicationRepository.findByVolunteer(volunteer);
            for (Application application : applications) {
                application.updateDeletedVolunteer(deletedVolunteer);
            }

            entityManager.flush();

            bookmarkRepository.deleteByVolunteerId(volunteer.getId());
            volunteerBadgeRepository.deleteByVolunteerId(volunteer.getId());
            volunteerRepository.delete(volunteer);
        } catch (Exception e) {
            log.error("봉사자 탈퇴 도중에 에러가 발생했습니다. {}", e.getMessage());
            throw new BadRequestException(VOLUNTEER_WITHDRAW_FAILED);
        }
    }
}
