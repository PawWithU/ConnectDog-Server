package com.pawwithu.connectdog.domain.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ApplicationVolunteerCompletedResponse(Long postId, String mainImage,
                                                    String departureLoc, String arrivalLoc,
                                                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                    LocalDate startDate,
                                                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                                    LocalDate endDate,
                                                    String intermediaryName, Boolean isKennel,
                                                    Long reviewId, Long dogStatusId) {

}
