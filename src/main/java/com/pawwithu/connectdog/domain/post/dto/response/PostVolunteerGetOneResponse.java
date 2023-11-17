package com.pawwithu.connectdog.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawwithu.connectdog.domain.dog.entity.DogGender;
import com.pawwithu.connectdog.domain.dog.entity.DogSize;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;

import java.time.LocalDate;
import java.util.List;

public record PostVolunteerGetOneResponse(Long postId, String mainImage, List<String> images, String postStatus,
                                          String departureLoc, String arrivalLoc,
                                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                 LocalDate startDate,
                                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                 LocalDate endDate,
                                          String pickUpTime,
                                          Boolean isKennel, String content,
                                          String dogName, String dogSize, String dogGender, Float dogWeight, String specifics,
                                          Long intermediaryId, String intermediaryProfileImage, String intermediaryName,
                                          Boolean isBookmark) {

    // 공고 이미지 필드를 제외한 생성자
    public PostVolunteerGetOneResponse(Long postId, String mainImage, PostStatus postStatus, String departureLoc, String arrivalLoc,
                                       LocalDate startDate, LocalDate endDate, String pickUpTime, Boolean isKennel, String content, String dogName,
                                       DogSize dogSize, DogGender dogGender, Float dogWeight, String specifics, Long intermediaryId,
                                       String intermediaryProfileImage, String intermediaryName) {
        this(postId, mainImage, null, postStatus.getKey(), departureLoc, arrivalLoc, startDate, endDate,
                pickUpTime, isKennel, content, dogName, dogSize.getKey(), dogGender.getKey(), dogWeight, specifics,
                intermediaryId, intermediaryProfileImage, intermediaryName, null);
    }

    // 공고 이미지, 북마크 여부를 포함한 생성자
    public static PostVolunteerGetOneResponse of(PostVolunteerGetOneResponse response, List<String> images, Boolean isBookmark) {
        return new PostVolunteerGetOneResponse(response.postId, response.mainImage, images, response.postStatus, response.departureLoc, response.arrivalLoc,
                response.startDate, response.endDate, response.pickUpTime, response.isKennel, response.content,
                response.dogName, response.dogSize, response.dogGender, response.dogWeight, response.specifics,
                response.intermediaryId, response.intermediaryProfileImage, response.intermediaryName, isBookmark);
    }

}
