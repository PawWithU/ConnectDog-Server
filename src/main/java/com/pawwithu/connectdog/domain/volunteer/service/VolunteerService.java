package com.pawwithu.connectdog.domain.volunteer.service;

import com.pawwithu.connectdog.domain.application.entity.ApplicationStatus;
import com.pawwithu.connectdog.domain.application.repository.CustomApplicationRepository;
import com.pawwithu.connectdog.domain.badge.repository.CustomVolunteerBadgeRepository;
import com.pawwithu.connectdog.domain.bookmark.repository.CustomBookmarkRepository;
import com.pawwithu.connectdog.domain.dogStatus.repository.CustomDogStatusRepository;
import com.pawwithu.connectdog.domain.review.repository.CustomReviewRepository;
import com.pawwithu.connectdog.domain.volunteer.dto.request.AdditionalAuthRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.NicknameRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.VolunteerMyProfileRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.response.*;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.ErrorCode;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pawwithu.connectdog.domain.application.entity.QApplication.application;
import static com.pawwithu.connectdog.error.ErrorCode.ALREADY_EXIST_NICKNAME;
import static com.pawwithu.connectdog.error.ErrorCode.VOLUNTEER_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final CustomApplicationRepository customApplicationRepository;
    private final CustomReviewRepository customReviewRepository;
    private final CustomDogStatusRepository customDogStatusRepository;
    private final CustomBookmarkRepository customBookmarkRepository;
    private final CustomVolunteerBadgeRepository customVolunteerBadgeRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional(readOnly = true)
    public VolunteerGetMyInfoResponse getMyInfo(String email) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));

        Long waitingCount = 0L;
        Long progressingCount = 0L;
        Long completedCount = 0L;
        Long reviewCount = customReviewRepository.getVolunteerCountOfReviews(volunteer.getId());

        List<Tuple> applicationCounts = customApplicationRepository.getCountOfApplicationsByStatus(volunteer.getId());
        for (Tuple tuple : applicationCounts) {
            ApplicationStatus status = tuple.get(application.status);
            Long count = tuple.get(application.count());

            switch (status) {
                case WAITING -> waitingCount = count;
                case PROGRESSING -> progressingCount = count;
                case COMPLETED -> completedCount = count;
            }
        }

        VolunteerGetMyInfoResponse response = VolunteerGetMyInfoResponse.of(volunteer.getProfileImageNum(), volunteer.getNickname(),
                waitingCount, progressingCount, completedCount, reviewCount);
        return response;
    }

    @Transactional(readOnly = true)
    public List<VolunteerGetMyBookmarkResponse> getMyBookmarks(String email) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));

        List<VolunteerGetMyBookmarkResponse> bookmarks = customBookmarkRepository.getMyBookmarks(volunteer.getId());
        return bookmarks;
    }

    @Transactional(readOnly = true)
    public List<VolunteerGetMyBadgeResponse> getMyBadges(String email) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));

        List<VolunteerGetMyBadgeResponse> badges = customVolunteerBadgeRepository.getMyBadges(volunteer.getId());
        return badges;
    }

    public void volunteerMyProfile(String email, VolunteerMyProfileRequest volunteerMyProfileRequest) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        String curNickname = volunteer.getNickname();
        String nickname = volunteerMyProfileRequest.nickname();
        Integer profileImageNum = volunteerMyProfileRequest.profileImageNum();

        if (curNickname.equals(nickname)) {
            volunteer.updateProfileImage(profileImageNum);
        } else {
            if (volunteerRepository.existsByNickname(nickname)) {
                throw new BadRequestException(ALREADY_EXIST_NICKNAME);
            }

            volunteer.updateMyProfile(nickname, profileImageNum);
        }
    }

    @Transactional(readOnly = true)
    public VolunteerGetProfileResponse getMyProfile(String email) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        VolunteerGetProfileResponse profile = VolunteerGetProfileResponse.of(volunteer.getProfileImageNum(), volunteer.getNickname());
        return profile;
    }

    public void changePassword(String email, String password) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        volunteer.updatePassword(password);
        volunteer.passwordEncode(passwordEncoder);
    }

    public void changeNotification(String email) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        volunteer.updateNotification();
    }
}
