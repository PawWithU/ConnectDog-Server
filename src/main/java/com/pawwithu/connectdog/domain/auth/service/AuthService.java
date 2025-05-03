package com.pawwithu.connectdog.domain.auth.service;

import com.pawwithu.connectdog.common.s3.FileService;
import com.pawwithu.connectdog.domain.application.entity.Application;
import com.pawwithu.connectdog.domain.application.entity.ApplicationStatus;
import com.pawwithu.connectdog.domain.application.repository.ApplicationRepository;
import com.pawwithu.connectdog.domain.auth.dto.request.*;
import com.pawwithu.connectdog.domain.auth.dto.response.*;
import com.pawwithu.connectdog.domain.badge.repository.VolunteerBadgeRepository;
import com.pawwithu.connectdog.domain.bookmark.repository.BookmarkRepository;
import com.pawwithu.connectdog.domain.dogStatus.repository.DogStatusImageRepository;
import com.pawwithu.connectdog.domain.dogStatus.repository.DogStatusRepository;
import com.pawwithu.connectdog.domain.fcm.repository.IntermediaryFcmRepository;
import com.pawwithu.connectdog.domain.fcm.repository.VolunteerFcmRepository;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.notification.repository.IntermediaryNotificationRepository;
import com.pawwithu.connectdog.domain.notification.repository.VolunteerNotificationRepository;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.repository.PostRepository;
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
    private final PostRepository postRepository;
    private final IntermediaryNotificationRepository intermediaryNotificationRepository;
    private final VolunteerNotificationRepository volunteerNotificationRepository;
    private final DogStatusRepository dogStatusRepository;
    private final DogStatusImageRepository dogStatusImageRepository;

    public void volunteerSignUp(VolunteerSignUpRequest request) {

        if (volunteerRepository.existsByPhone(request.phone())) {
            throw new BadRequestException(ALREADY_EXIST_PHONE);
        }
        if (volunteerRepository.existsByEmail(request.email())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL);
        }
        if (volunteerRepository.existsByNickname(request.nickname())) {
            throw new BadRequestException(ALREADY_EXIST_NICKNAME);
        }

        Volunteer volunteer = request.toEntity();
        volunteer.passwordEncode(passwordEncoder);
        volunteerRepository.save(volunteer);
    }

    public void intermediarySignUp(IntermediarySignUpRequest request, MultipartFile profileFile) {

        if (intermediaryRepository.existsByPhone(request.phone())) {
            throw new BadRequestException(ALREADY_EXIST_PHONE);
        }
        if (intermediaryRepository.existsByEmail(request.email())) {
            throw new BadRequestException(ALREADY_EXIST_EMAIL);
        }
        if (intermediaryRepository.existsByName(request.name())) {
            throw new BadRequestException(ALREADY_EXIST_NAME);
        }
        String profileImage = fileService.uploadFile(profileFile, "intermediary/profileImage");
        if (profileImage == null) {
            throw new BadRequestException(FILE_NOT_FOUND);
        }
        Intermediary intermediary = IntermediarySignUpRequest.toEntity(request, profileImage);
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
        volunteer.updateSocialVolunteer(name, phone, nickname, VolunteerRole.AUTH_VOLUNTEER, profileImageNum, isOptionAgr); // GUEST -> AUTH_VOLUNTEER
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

            Volunteer deletedVolunteer = volunteerRepository.findByEmail("deletedVolunteer@connectdog.com").orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
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
            volunteerNotificationRepository.deleteByVolunteerId(volunteer.getId());
            volunteerFcmRepository.deleteByVolunteerId(volunteer.getId());
            volunteerRepository.delete(volunteer);
        } catch (Exception e) {
            log.error("봉사자 탈퇴 도중에 에러가 발생했습니다. {}", e.getMessage());
            throw new BadRequestException(VOLUNTEER_WITHDRAW_FAILED);
        }
    }

    public void intermediariesWithdraw(HttpServletRequest request, String email) {
        String accessToken = jwtService.extractAccessToken(request).orElseThrow(() -> new BadRequestException(TOKEN_NOT_EXIST));
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        String roleName = jwtService.extractRoleName(accessToken).orElseThrow(() -> new BadRequestException(NOT_FOUND_ROLE_NAME));

        try {
            redisUtil.delete(roleName, intermediary.getId());
            volunteerFcmRepository.deleteByVolunteerId(intermediary.getId());
            redisUtil.setBlackList(accessToken, "accessToken", jwtService.getAccessTokenExpirationPeriod());

            Intermediary deletedIntermediary = intermediaryRepository.findByEmail("deletedIntermediary@connectdog.com").orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));

            List<Post> posts = postRepository.findByIntermediary(intermediary);
            for (Post post : posts) {
                post.updateDeletedIntermediary(deletedIntermediary);
            }

            List<Application> applications = applicationRepository.findByIntermediary(intermediary);
            for (Application application : applications) {
                application.updateDeletedIntermediary(deletedIntermediary);
            }

            entityManager.flush();

            intermediaryNotificationRepository.deleteByIntermediaryId(intermediary.getId());
            intermediaryFcmRepository.deleteByIntermediaryId(intermediary.getId());
            intermediaryRepository.delete(intermediary);
        } catch (Exception e) {
            log.error("모집자 탈퇴 도중에 에러가 발생했습니다. {}", e.getMessage());
            throw new BadRequestException(INTERMEDIARY_WITHDRAW_FAILED);
        }
    }

    @Transactional(readOnly = true)
    public IntermediaryNameResponse isIntermediaryNameDuplicated(IntermediaryNameRequest request) {
        Boolean isDuplicated = intermediaryRepository.existsByName(request.name());
        IntermediaryNameResponse response = IntermediaryNameResponse.of(isDuplicated);
        return response;
    }

    @Transactional(readOnly = true)
    public VolunteerEmailResponse findVolunteerEmail(VolunteerPhoneRequest request) {
        Volunteer volunteer = volunteerRepository.findByPhone(request.phone()).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        VolunteerEmailResponse response = VolunteerEmailResponse.of(volunteer.getEmail());
        return response;
    }

    @Transactional(readOnly = true)
    public IntermediaryEmailResponse findIntermediaryEmail(IntermediaryPhoneRequest request) {
        Intermediary intermediary = intermediaryRepository.findByPhone(request.phone()).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        IntermediaryEmailResponse response = IntermediaryEmailResponse.of(intermediary.getEmail());
        return response;
    }

    @Transactional(readOnly = true)
    public Boolean checkVolunteerWithdraw(String email) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        Boolean response = applicationRepository.existsByVolunteerAndStatusIn(volunteer, List.of(ApplicationStatus.WAITING, ApplicationStatus.PROGRESSING));
        return !response;
    }

    @Transactional(readOnly = true)
    public Boolean checkIntermediaryWithdraw(String email) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        Boolean response = applicationRepository.existsByIntermediaryAndStatusIn(intermediary, List.of(ApplicationStatus.WAITING, ApplicationStatus.PROGRESSING));
        return !response;
    }
}
