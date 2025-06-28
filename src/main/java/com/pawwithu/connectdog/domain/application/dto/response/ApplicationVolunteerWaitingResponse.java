package com.pawwithu.connectdog.domain.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawwithu.connectdog.domain.dog.entity.DogSize;

import java.time.LocalDate;

public record ApplicationVolunteerWaitingResponse(Long postId, String mainImage, String dogName, String departureLoc, String arrivalLoc,
                                                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                  LocalDate startDate,
                                                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                  LocalDate endDate,
                                                  Boolean isAdjust,
                                                  String pickUpTime,
                                                  String dogSize,
                                                  Boolean isKennel,
                                                  Long applicationId) {

    public ApplicationVolunteerWaitingResponse(Long postId, String mainImage, String dogName, String departureLoc, String arrivalLoc,
                                               LocalDate startDate, LocalDate endDate, Boolean isAdjust, String pickUpTime,
                                               DogSize dogSize, Boolean isKennel, Long applicationId) {
        this(postId, mainImage, dogName, departureLoc, arrivalLoc, startDate, endDate, isAdjust, pickUpTime,
                dogSize.getKey(), isKennel, applicationId);
    }
}
