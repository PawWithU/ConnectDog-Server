package com.pawwithu.connectdog.domain.intermediary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetOneResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record IntermediaryGetReviewsResponse(Integer profileImageNum, String dogName, String volunteerNickname,
                                             @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                             LocalDate createdDate,
                                             String mainImage, List<String> images, String content,
                                             Long postId, String postMainImage,
                                             @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                             LocalDate startDate,
                                             @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                             LocalDate endDate,
                                             String departureLoc, String arrivalLoc,
                                             Long intermediaryId, String intermediaryName
) {

    // 후기 이미지 리스트 필드를 제외한 생성자
    public IntermediaryGetReviewsResponse(Integer profileImageNum, String dogName, String volunteerNickname, LocalDateTime createdDate,
                                String mainImage, String content,
                                Long postId, String postMainImage, LocalDate startDate, LocalDate endDate, String departureLoc, String arrivalLoc,
                                Long intermediaryId, String intermediaryName) {
        this(profileImageNum, dogName, volunteerNickname, createdDate.toLocalDate(), mainImage, null, content,
                postId, postMainImage, startDate, endDate, departureLoc, arrivalLoc, intermediaryId, intermediaryName);
    }

    // 후기 이미지 리스트 필드를 포함한 생성자
    public static IntermediaryGetReviewsResponse of(IntermediaryGetReviewsResponse response, List<String> images) {
        return new IntermediaryGetReviewsResponse(response.profileImageNum, response.dogName, response.volunteerNickname, response.createdDate,
                response.mainImage, images, response.content,
                response.postId, response.postMainImage, response.startDate, response.endDate, response.departureLoc, response.arrivalLoc, response.intermediaryId, response.intermediaryName);
    }

}