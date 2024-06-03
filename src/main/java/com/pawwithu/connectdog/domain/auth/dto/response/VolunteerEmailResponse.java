package com.pawwithu.connectdog.domain.auth.dto.response;

public record VolunteerEmailResponse(String email) {
    public static VolunteerEmailResponse of(String email){
        return new VolunteerEmailResponse(email);
    }
}