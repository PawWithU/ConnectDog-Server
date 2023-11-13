package com.pawwithu.connectdog.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record ReviewGetOneResponse(String dogName, String volunteerNickname, String mainImage, List<String> images,
                                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                LocalDate startDate,
                                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                LocalDate endDate,
                                   String departureLoc, String arrivalLoc,
                                   String intermediaryName, String content
                                ) {

    // 리뷰 이미지 리스트 필드를 제외한 생성자
    public ReviewGetOneResponse(String dogName, String volunteerNickname, String mainImage,
                                LocalDate startDate, LocalDate endDate, String departureLoc, String arrivalLoc,
                                String intermediaryName, String content) {
        this(dogName, volunteerNickname, mainImage, null, startDate, endDate, departureLoc, arrivalLoc, intermediaryName, content);
    }

    // 리뷰 이미지 리스트 필드를 포함한 생성자
    public static ReviewGetOneResponse of(ReviewGetOneResponse response, List<String> images) {
        return new ReviewGetOneResponse(response.dogName, response.volunteerNickname, response.mainImage, images,
                response.startDate, response.endDate, response.departureLoc, response.arrivalLoc, response.intermediaryName, response.content);
    }

}
