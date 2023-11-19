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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional(readOnly = true)
    public List<IntermediaryGetPostsResponse> getIntermediaryPosts(Long intermediaryId, Pageable pageable) {
        // 이동봉사 중개
        if (!intermediaryRepository.existsById(intermediaryId)){
            throw new BadRequestException(INTERMEDIARY_NOT_FOUND);
        }
        List<IntermediaryGetPostsResponse> intermediaryPosts = customPostRepository.getIntermediaryPosts(intermediaryId, pageable);
        return intermediaryPosts;
    }

    @Transactional(readOnly = true)
    public IntermediaryGetInfoResponse getIntermediaryInfo(Long intermediaryId) {
        Intermediary intermediary = intermediaryRepository.findById(intermediaryId).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        // 봉사 완료 건수
        Long completedPostCount =  customPostRepository.getCountOfCompletedPosts(intermediaryId);

        // 받은 후기 총 건수
        Long reviewCount = customReviewRepository.getIntermediaryCountOfReviews(intermediaryId);

        // 남긴 근황 총 건수
        Long dogStatusCount = customDogStatusRepository.getIntermediaryCountOfDogStatuses(intermediaryId);

        IntermediaryGetInfoResponse intermediaryInfo = IntermediaryGetInfoResponse.from(intermediary, completedPostCount, reviewCount, dogStatusCount);
        return intermediaryInfo;
    }

    @Transactional(readOnly = true)
    public List<IntermediaryGetReviewsResponse> getIntermediaryReviews(Long intermediaryId, Pageable pageable) {
        // 이동봉사 중개
        if (!intermediaryRepository.existsById(intermediaryId)){
            throw new BadRequestException(INTERMEDIARY_NOT_FOUND);
        }
        List<IntermediaryGetReviewsResponse> intermediaryReviews = customReviewRepository.getIntermediaryReviews(intermediaryId, pageable);
        return intermediaryReviews;
    }

    @Transactional(readOnly = true)
    public List<IntermediaryGetDogStatusesResponse> getIntermediaryDogStatuses(Long intermediaryId, Pageable pageable) {
        // 이동봉사 중개
        if (!intermediaryRepository.existsById(intermediaryId)){
            throw new BadRequestException(INTERMEDIARY_NOT_FOUND);
        }
        List<IntermediaryGetDogStatusesResponse> intermediaryDogStatuses = customDogStatusRepository.getIntermediaryDogStatuses(intermediaryId, pageable);
        return intermediaryDogStatuses;
    }

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
}
