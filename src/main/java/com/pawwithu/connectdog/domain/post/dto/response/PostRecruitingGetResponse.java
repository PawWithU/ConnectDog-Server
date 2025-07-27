package com.pawwithu.connectdog.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PostRecruitingGetResponse(Long postId, String postStatus, String mainImage, String dogName,
                                        String departureLoc, String arrivalLoc,
                                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                        LocalDate startDate,
                                        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
                                        LocalDate endDate,
                                        Boolean isAdjust,
                                        String pickUpTime,
                                        String dogSize,
                                        Boolean isKennel,
                                        Boolean boost) {

    // 끌어올리기 가능 여부 포함
    public static PostRecruitingGetResponse of(PostRecruitingGetResponseWithBoostDate response, Boolean boost) {
        return new PostRecruitingGetResponse(response.postId(), response.postStatus(), response.mainImage(),
                response.dogName(), response.departureLoc(), response.arrivalLoc(),
                response.startDate(), response.endDate(), response.isAdjust(), response.pickUpTime(), response.dogSize(),
                response.isKennel(), boost);
    }
}
