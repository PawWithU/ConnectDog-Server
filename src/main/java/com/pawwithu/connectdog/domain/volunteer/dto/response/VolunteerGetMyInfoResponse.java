package com.pawwithu.connectdog.domain.volunteer.dto.response;

public record VolunteerGetMyInfoResponse(Long completedCount, Long reviewCount, Long dogStatusCount) {
    public static VolunteerGetMyInfoResponse of(Long completedCount, Long reviewCount, Long dogStatusCount) {
        return new VolunteerGetMyInfoResponse(completedCount, reviewCount, dogStatusCount);
    }
}
