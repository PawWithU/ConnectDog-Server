package com.pawwithu.connectdog.domain.intermediary.dto.response;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;

public record IntermediaryGetInfoResponse(String profileImage, Long completedPostCount, String name, String intro,
                                          String url, String contact, String guide,
                                          Long reviewCount, Long dogStatusCount) {
    public static IntermediaryGetInfoResponse from(Intermediary intermediary, Long completedPostCount, Long reviewCount, Long dogStatusCount) {
        return new IntermediaryGetInfoResponse(intermediary.getProfileImage(), completedPostCount, intermediary.getName(), intermediary.getIntro(),
                intermediary.getUrl(), intermediary.getContact(), intermediary.getGuide(), reviewCount, dogStatusCount);
    }
}
