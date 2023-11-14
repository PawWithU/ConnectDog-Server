package com.pawwithu.connectdog.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;

import java.time.LocalDate;

public record PostRecruitingGetResponse(Long postId, String postStatus, String mainImage, String dogName, String departureLoc, String arrivalLoc,
                                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                     LocalDate startDate,
                                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                     LocalDate endDate,
                                        String volunteerName) {

    // 공고 이동봉사자 이름을 제외한 생성자
    public PostRecruitingGetResponse(Long postId, PostStatus postStatus, String mainImage, String dogName, String departureLoc, String arrivalLoc,
                                     LocalDate startDate, LocalDate endDate) {
        this(postId, postStatus.getKey(), mainImage, dogName, departureLoc, arrivalLoc, startDate, endDate, null);
    }
}
