package com.pawwithu.connectdog.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ReviewGetAllResponse(Long reviewId, Integer profileImageNum, String dogName, String volunteerNickname,
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
    public ReviewGetAllResponse(Long reviewId, Integer profileImageNum, String dogName, String volunteerNickname, LocalDateTime createdDate,
                                String mainImage, String content,
                                Long postId, String postMainImage, LocalDate startDate, LocalDate endDate, String departureLoc, String arrivalLoc,
                                Long intermediaryId, String intermediaryName) {
        this(reviewId, profileImageNum, dogName, volunteerNickname, createdDate.toLocalDate(), mainImage, null, content,
                postId, postMainImage, startDate, endDate, departureLoc, arrivalLoc, intermediaryId, intermediaryName);
    }

    // 후기 이미지 리스트 필드를 포함한 생성자
    public static ReviewGetAllResponse of(ReviewGetAllResponse response, List<String> images) {
        return new ReviewGetAllResponse(response.reviewId, response.profileImageNum, response.dogName, response.volunteerNickname, response.createdDate,
                response.mainImage, images, response.content,
                response.postId, response.postMainImage, response.startDate, response.endDate, response.departureLoc, response.arrivalLoc, response.intermediaryId, response.intermediaryName);
    }

}

