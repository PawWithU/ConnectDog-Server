package com.pawwithu.connectdog.domain.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ApplicationIntermediaryProgressingResponse(Long postId, String mainImage, String dogName,
                                                         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                         LocalDate startDate,
                                                         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                         LocalDate endDate,
                                                         String departureLoc, String arrivalLoc,
                                                         String volunteerName,
                                                         Long applicationId) {
}
