package com.pawwithu.connectdog.domain.intermediary.dto.response;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;

public record IntermediaryGetHomeResponse(String profileImage, String intermediaryName, String intro, Long recruitingCount,
                                          Long waitingCount, Long progressingCount, Long completedCount) {

    public static IntermediaryGetHomeResponse of(Intermediary intermediary, Long recruitingCount, Long waitingCount,
                                                 Long progressingCount, Long completedCount) {
        return new IntermediaryGetHomeResponse(intermediary.getProfileImage(), intermediary.getName(), intermediary.getIntro(),
                recruitingCount, waitingCount, progressingCount, completedCount);
    }

}
