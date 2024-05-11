package com.pawwithu.connectdog.domain.intermediary.dto.response;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;

public record IntermediaryGetInfoResponse(String profileImage, String name, String intro,
                                          String url, String contact, String guide) {
    public static IntermediaryGetInfoResponse from(Intermediary intermediary) {
        return new IntermediaryGetInfoResponse(intermediary.getProfileImage(), intermediary.getName(), intermediary.getIntro(),
                intermediary.getUrl(), intermediary.getContact(), intermediary.getGuide());
    }
}
