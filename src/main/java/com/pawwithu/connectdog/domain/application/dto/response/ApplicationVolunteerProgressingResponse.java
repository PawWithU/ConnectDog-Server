package com.pawwithu.connectdog.domain.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawwithu.connectdog.domain.dog.entity.DogSize;

import java.time.LocalDate;

public record ApplicationVolunteerProgressingResponse(Long postId, String mainImage, String dogName, String departureLoc, String arrivalLoc,
                                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                      LocalDate startDate,
                                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                      LocalDate endDate,
                                                      String pickUpTime,
                                                      String dogSize,
                                                      Boolean isKennel,
                                                      Long applicationId) {

    public ApplicationVolunteerProgressingResponse(Long postId, String mainImage, String dogName, String departureLoc, String arrivalLoc,
                                               LocalDate startDate, LocalDate endDate, String pickUpTime,
                                               DogSize dogSize, Boolean isKennel, Long applicationId) {
        this(postId, mainImage, dogName, departureLoc, arrivalLoc, startDate, endDate, pickUpTime,
                dogSize.getKey(), isKennel, applicationId);
    }
}