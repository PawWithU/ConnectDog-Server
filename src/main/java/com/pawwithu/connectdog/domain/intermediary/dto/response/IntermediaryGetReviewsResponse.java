package com.pawwithu.connectdog.domain.intermediary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

public record IntermediaryGetReviewsResponse(Integer profileImageNum, String dogName, String volunteerNickname, String mainImage, List<String> images,
                                             @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                             LocalDate startDate,
                                             @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                             LocalDate endDate,
                                             String departureLoc, String arrivalLoc,
                                             String intermediaryName, String content
) {

    // 후기 이미지 리스트 필드를 제외한 생성자
    public IntermediaryGetReviewsResponse(Integer profileImageNum, String dogName, String volunteerNickname, String mainImage,
                                LocalDate startDate, LocalDate endDate, String departureLoc, String arrivalLoc,
                                String intermediaryName, String content) {
        this(profileImageNum, dogName, volunteerNickname, mainImage, null, startDate, endDate, departureLoc, arrivalLoc, intermediaryName, content);
    }

    // 후기 이미지 리스트 필드를 포함한 생성자
    public static IntermediaryGetReviewsResponse of(IntermediaryGetReviewsResponse response, List<String> images) {
        return new IntermediaryGetReviewsResponse(response.profileImageNum, response.dogName, response.volunteerNickname, response.mainImage, images,
                response.startDate, response.endDate, response.departureLoc, response.arrivalLoc, response.intermediaryName, response.content);
    }
}
