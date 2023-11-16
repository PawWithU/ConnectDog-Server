package com.pawwithu.connectdog.domain.intermediary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record IntermediaryGetPostsResponse(Long postId, String mainImage, String departureLoc, String arrivalLoc,
                                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                           LocalDate startDate,
                                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                           LocalDate endDate,
                                           String intermediaryName,
                                           Boolean isKennel) {
}
