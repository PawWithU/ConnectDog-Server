package com.pawwithu.connectdog.domain.volunteer.service;

import com.pawwithu.connectdog.domain.application.repository.CustomApplicationRepository;
import com.pawwithu.connectdog.domain.badge.repository.CustomVolunteerBadgeRepository;
import com.pawwithu.connectdog.domain.bookmark.repository.CustomBookmarkRepository;
import com.pawwithu.connectdog.domain.dogStatus.repository.CustomDogStatusRepository;
import com.pawwithu.connectdog.domain.review.repository.CustomReviewRepository;
import com.pawwithu.connectdog.domain.volunteer.dto.request.AdditionalAuthRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.NicknameRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.VolunteerMyProfileRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.response.NicknameResponse;
import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBadgeResponse;
import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBookmarkResponse;
import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyInfoResponse;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.ErrorCode;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        // 진행한 이동봉사 건수
        Long completedCount = customApplicationRepository.getCountOfCompletedApplications(volunteer.getId());

        // 봉사 후기 건수
        Long reviewCount = customReviewRepository.getVolunteerCountOfReviews(volunteer.getId());

        // 입양 근황 건수
        Long dogStatusCount = customDogStatusRepository.getVolunteerCountOfDogStatuses(volunteer.getId());

        VolunteerGetMyInfoResponse response = VolunteerGetMyInfoResponse.of(volunteer.getProfileImageNum(), volunteer.getNickname(), completedCount, reviewCount, dogStatusCount);
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
}
