package com.pawwithu.connectdog.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record ReviewGetOneResponse(Integer profileImageNum, String dogName, String volunteerNickname,
                                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                   LocalDate createdDate,
                                   String mainImage, List<String> images, String content,
                                   String postMainImage,
                                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                   LocalDate startDate,
                                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                   LocalDate endDate,
                                   String departureLoc, String arrivalLoc,
                                   Long intermediaryId, String intermediaryName
) {

    // 후기 이미지 리스트 필드를 제외한 생성자
    public ReviewGetOneResponse(Integer profileImageNum, String dogName, String volunteerNickname, LocalDate createdDate,
                                String mainImage, String content,
                                String postMainImage, LocalDate startDate, LocalDate endDate, String departureLoc, String arrivalLoc,
                                Long intermediaryId, String intermediaryName) {
        this(profileImageNum, dogName, volunteerNickname, createdDate, mainImage, null, content, postMainImage, startDate, endDate, departureLoc, arrivalLoc, intermediaryId, intermediaryName);
    }

    // 후기 이미지 리스트 필드를 함한 생성자
    public static ReviewGetOneResponse of(ReviewGetOneResponse response, List<String> images) {
        return new ReviewGetOneResponse(response.profileImageNum, response.dogName, response.volunteerNickname, response.createdDate,
                response.mainImage, images, response.content,
                response.postMainImage, response.startDate, response.endDate, response.departureLoc, response.arrivalLoc, response.intermediaryId, response.intermediaryName);
    }

}
