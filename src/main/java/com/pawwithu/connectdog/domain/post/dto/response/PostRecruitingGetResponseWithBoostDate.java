package com.pawwithu.connectdog.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawwithu.connectdog.domain.dog.entity.DogSize;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PostRecruitingGetResponseWithBoostDate(Long postId, String postStatus, String mainImage, String dogName,
                                                     String departureLoc, String arrivalLoc,
                                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                     LocalDate startDate,
                                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                     LocalDate endDate,
                                                     Boolean isAdjust,
                                                     String pickUpTime,
                                                     String dogSize,
                                                     Boolean isKennel,
                                                     LocalDateTime boostDate) {
    // 공고 이동봉사자 이름을 제외한 생성자
    public PostRecruitingGetResponseWithBoostDate(Long postId, PostStatus postStatus, String mainImage, String dogName,
                                                  String departureLoc, String arrivalLoc, LocalDate startDate, LocalDate endDate, Boolean isAdjust,
                                                  String pickUpTime, DogSize dogSize, Boolean isKennel, LocalDateTime boostDate) {
        this(postId, postStatus.getKey(), mainImage, dogName, departureLoc, arrivalLoc,
                startDate, endDate, isAdjust, pickUpTime, dogSize.getKey(), isKennel, boostDate);
    }
}