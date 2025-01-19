package com.pawwithu.connectdog.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawwithu.connectdog.domain.dog.entity.DogSize;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record PostIntermediaryGetOneResponse(Long postId, String mainImage, List<String> images, String postStatus,
                                             String departureLoc, String arrivalLoc,
                                             @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                             LocalDate startDate,
                                             @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                             LocalDate endDate,
                                             String pickUpTime,
                                             Boolean isKennel, String content,
                                             String dogName, String dogSize, String specifics,
                                             Long intermediaryId, String intermediaryProfileImage, String intermediaryName,
                                             Boolean boost, String leftDate) {

    // 공고 이미지 필드를 제외한 생성자
    public PostIntermediaryGetOneResponse(Long postId, String mainImage, PostStatus postStatus, String departureLoc, String arrivalLoc,
                              LocalDate startDate, LocalDate endDate, String pickUpTime, Boolean isKennel, String content, String dogName,
                              DogSize dogSize, String specifics, Long intermediaryId,
                              String intermediaryProfileImage, String intermediaryName, LocalDateTime leftDate) {
        this(postId, mainImage, null, postStatus.getKey(), departureLoc, arrivalLoc, startDate, endDate,
                pickUpTime, isKennel, content, dogName, dogSize.getKey(), specifics,
                intermediaryId, intermediaryProfileImage, intermediaryName, null, leftDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    // 공고 이미지를 포함한 생성자
    public static PostIntermediaryGetOneResponse of(PostIntermediaryGetOneResponse response, List<String> images, Boolean boost, String leftDate) {
        return new PostIntermediaryGetOneResponse(response.postId, response.mainImage, images, response.postStatus, response.departureLoc, response.arrivalLoc,
                response.startDate, response.endDate, response.pickUpTime, response.isKennel, response.content,
                response.dogName, response.dogSize, response.specifics,
                response.intermediaryId, response.intermediaryProfileImage, response.intermediaryName, boost, leftDate);
    }
}
