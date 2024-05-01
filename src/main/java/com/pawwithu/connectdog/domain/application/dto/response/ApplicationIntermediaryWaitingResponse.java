package com.pawwithu.connectdog.domain.application.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pawwithu.connectdog.domain.dog.entity.DogSize;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ApplicationIntermediaryWaitingResponse(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
                                                     LocalDateTime applicationTime,
                                                     Long postId, String mainImage, String dogName,
                                                     String departureLoc, String arrivalLoc,
                                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                     LocalDate startDate,
                                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                     LocalDate endDate,
                                                     String pickUpTime,
                                                     String dogSize,
                                                     Boolean isKennel,
                                                     Long applicationId) {

    public ApplicationIntermediaryWaitingResponse(LocalDateTime applicationTime, Long postId, String mainImage, String dogName,
                                                  String departureLoc, String arrivalLoc, LocalDate startDate, LocalDate endDate,
                                                  String pickUpTime, DogSize dogSize, Boolean isKennel, Long applicationId) {
        this(applicationTime, postId, mainImage, dogName, departureLoc, arrivalLoc, startDate, endDate, pickUpTime,
                dogSize.getKey(), isKennel, applicationId);
    }
}
