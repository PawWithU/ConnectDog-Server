package com.pawwithu.connectdog.domain.review.service;

import com.pawwithu.connectdog.common.s3.FileService;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.repository.PostRepository;
import com.pawwithu.connectdog.domain.review.dto.request.ReviewCreateRequest;
import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetResponse;
import com.pawwithu.connectdog.domain.review.entity.Review;
import com.pawwithu.connectdog.domain.review.entity.ReviewImage;
import com.pawwithu.connectdog.domain.review.repository.CustomReviewRepository;
import com.pawwithu.connectdog.domain.review.repository.ReviewImageRepository;
import com.pawwithu.connectdog.domain.review.repository.ReviewRepository;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.pawwithu.connectdog.error.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final FileService fileService;
    private final VolunteerRepository volunteerRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final CustomReviewRepository customReviewRepository;

    public void createReview(String email, Long postId, ReviewCreateRequest request, List<MultipartFile> fileList) {

        // 파일이 존재하지 않을 경우
        if (fileList.isEmpty())
            throw new BadRequestException(FILE_NOT_FOUND);

        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException(POST_NOT_FOUND));

        // 후기 저장 (대표 이미지 제외)
        Review review = ReviewCreateRequest.reviewToEntity(request, volunteer, post);
        Review saveReview = reviewRepository.save(review);

        // 후기 이미지 저장
        List<ReviewImage> reviewImages = fileList.stream()
                .map(f -> ReviewImage.builder()
                        .image(fileService.uploadFile(f, "volunteer/review"))
                        .review(saveReview)
                        .build())
                .collect(Collectors.toList());
        reviewImageRepository.saveAll(reviewImages);

        // 후기 대표 이미지 업데이트
        review.updateMainImage(reviewImages.get(0));
    }

    public ReviewGetResponse getOneReview(String email, Long reviewId) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        // 후기 조회 (대표 이미지 포함)
        ReviewGetResponse oneReview = customReviewRepository.getOneReview(volunteer.getId(), reviewId);
        // 후기 이미지 조회 (대표 이미지 제외)
        List<String> oneReviewImages = customReviewRepository.getOneReviewImages(reviewId);
        ReviewGetResponse response = ReviewGetResponse.of(oneReview, oneReviewImages);
        return response;
    }
}
