package com.pawwithu.connectdog.domain.volunteer.dto.response;

public record VolunteerGetMyInfoResponse(Integer profileImageNum, String nickname, Long completedCount, Long reviewCount, Long dogStatusCount) {
    public static VolunteerGetMyInfoResponse of(Integer profileImageNum, String nickname, Long completedCount, Long reviewCount, Long dogStatusCount) {
        return new VolunteerGetMyInfoResponse(profileImageNum, nickname, completedCount, reviewCount, dogStatusCount);
    }
}
