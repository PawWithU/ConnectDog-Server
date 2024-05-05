package com.pawwithu.connectdog.domain.volunteer.dto.response;

public record VolunteerGetMyInfoResponse(Integer profileImageNum, String nickname,
                                         Long waitingCount, Long progressingCount, Long completedCount, Long reviewCount) {

    public static VolunteerGetMyInfoResponse of(Integer profileImageNum, String nickname,
                                                Long waitingCount, Long progressingCount, Long completedCount, Long reviewCount) {
        return new VolunteerGetMyInfoResponse(profileImageNum, nickname, waitingCount, progressingCount, completedCount, reviewCount);
    }
}
