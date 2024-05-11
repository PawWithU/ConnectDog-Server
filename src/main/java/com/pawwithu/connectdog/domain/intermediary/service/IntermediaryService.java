package com.pawwithu.connectdog.domain.intermediary.service;

import com.pawwithu.connectdog.common.s3.FileService;
import com.pawwithu.connectdog.domain.dogStatus.repository.CustomDogStatusRepository;
import com.pawwithu.connectdog.domain.intermediary.dto.request.IntermediaryMyProfileRequest;
import com.pawwithu.connectdog.domain.intermediary.dto.response.*;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import com.pawwithu.connectdog.domain.post.repository.CustomPostRepository;
import com.pawwithu.connectdog.domain.review.repository.CustomReviewRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pawwithu.connectdog.error.ErrorCode.INTERMEDIARY_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IntermediaryService {

    private final IntermediaryRepository intermediaryRepository;
    private final CustomPostRepository customPostRepository;
    private final CustomReviewRepository customReviewRepository;
    private final CustomDogStatusRepository customDogStatusRepository;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<IntermediaryGetPostsResponse> volunteerGetIntermediaryPosts(Long intermediaryId, String orderCondition, Pageable pageable) {
        // 이동봉사 중개
        if (!intermediaryRepository.existsById(intermediaryId)) {
            throw new BadRequestException(INTERMEDIARY_NOT_FOUND);
        }
        List<IntermediaryGetPostsResponse> intermediaryPosts = customPostRepository.getIntermediaryPosts(intermediaryId, orderCondition, pageable);
        return intermediaryPosts;
    }

    @Transactional(readOnly = true)
    public IntermediaryGetInfoResponse getIntermediaryInfo(Long intermediaryId) {
        Intermediary intermediary = intermediaryRepository.findById(intermediaryId).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        IntermediaryGetInfoResponse intermediaryInfo = IntermediaryGetInfoResponse.from(intermediary);
        return intermediaryInfo;
    }

    @Transactional(readOnly = true)
    public List<IntermediaryGetReviewsResponse> volunteerGetIntermediaryReviews(Long intermediaryId, Pageable pageable) {
        // 이동봉사 중개
        if (!intermediaryRepository.existsById(intermediaryId)) {
            throw new BadRequestException(INTERMEDIARY_NOT_FOUND);
        }

        List<IntermediaryGetReviewsResponse> resultReviews = new ArrayList<>();

        // 후기 조회 (대표 이미지 포함)
        List<IntermediaryGetReviewsResponse> reviews = customReviewRepository.getIntermediaryReviews(intermediaryId, pageable);

        for (IntermediaryGetReviewsResponse intermediaryGetReviewsResponse : reviews) {
            // 후기 이미지 조회 (대표 이미지 제외)
            List<String> oneReviewImages = customReviewRepository.getOneReviewImages(intermediaryGetReviewsResponse.reviewId());
            IntermediaryGetReviewsResponse review = IntermediaryGetReviewsResponse.of(intermediaryGetReviewsResponse, oneReviewImages);
            resultReviews.add(review);
        }

        return resultReviews;
    }

    @Transactional(readOnly = true)
    public List<IntermediaryGetReviewsResponse> intermediaryGetIntermediaryReviews(String email, Pageable pageable) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        List<IntermediaryGetReviewsResponse> resultReviews = new ArrayList<>();

        // 후기 조회 (대표 이미지 포함)
        List<IntermediaryGetReviewsResponse> reviews = customReviewRepository.getIntermediaryReviews(intermediary.getId(), pageable);

        for (IntermediaryGetReviewsResponse intermediaryGetReviewsResponse : reviews) {
            // 후기 이미지 조회 (대표 이미지 제외)
            List<String> oneReviewImages = customReviewRepository.getOneReviewImages(intermediaryGetReviewsResponse.reviewId());
            IntermediaryGetReviewsResponse review = IntermediaryGetReviewsResponse.of(intermediaryGetReviewsResponse, oneReviewImages);
            resultReviews.add(review);
        }

        return resultReviews;
    }

    @Transactional(readOnly = true)
    public List<IntermediaryGetDogStatusesResponse> getIntermediaryDogStatuses(Long intermediaryId, Pageable pageable) {
        // 이동봉사 중개
        if (!intermediaryRepository.existsById(intermediaryId)) {
            throw new BadRequestException(INTERMEDIARY_NOT_FOUND);
        }
        List<IntermediaryGetDogStatusesResponse> intermediaryDogStatuses = customDogStatusRepository.getIntermediaryDogStatuses(intermediaryId, pageable);
        return intermediaryDogStatuses;
    }

    @Transactional(readOnly = true)
    public IntermediaryGetHomeResponse getIntermediaryHome(String email) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        Map<PostStatus, Long> countOfPostStatus = customPostRepository.getCountOfPostStatus(intermediary.getId(), null);
        IntermediaryGetHomeResponse response = IntermediaryGetHomeResponse.of(
                intermediary,
                countOfPostStatus.getOrDefault(PostStatus.RECRUITING, 0L),
                countOfPostStatus.getOrDefault(PostStatus.WAITING, 0L),
                countOfPostStatus.getOrDefault(PostStatus.PROGRESSING, 0L),
                countOfPostStatus.getOrDefault(PostStatus.COMPLETED, 0L));
        return response;
    }


    public void intermediaryMyProfile(String email, IntermediaryMyProfileRequest intermediaryMyProfileRequest, MultipartFile profileFile) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));

        String intro = intermediaryMyProfileRequest.intro();
        String contact = intermediaryMyProfileRequest.contact();
        String guide = intermediaryMyProfileRequest.guide();

        String profileImage = fileService.uploadFile(profileFile, "intermediary/profileImage");
        if (profileImage != null) {
            intermediary.updateProfile(profileImage, intro, contact, guide);
        } else {
            intermediary.updateProfileWithoutImage(intro, contact, guide);
        }

    }

    @Transactional(readOnly = true)
    public List<IntermediaryGetPostsResponse> intermediaryGetIntermediaryPosts(String email, String orderCondition, Pageable pageable) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        List<IntermediaryGetPostsResponse> intermediaryPosts = customPostRepository.getIntermediaryPosts(intermediary.getId(), orderCondition, pageable);
        return intermediaryPosts;
    }

    public void changePassword(String email, String password) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        intermediary.updatePassword(password);
        intermediary.passwordEncode(passwordEncoder);
    }
}
