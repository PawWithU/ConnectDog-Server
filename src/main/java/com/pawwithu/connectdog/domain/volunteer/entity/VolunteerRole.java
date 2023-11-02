package com.pawwithu.connectdog.domain.volunteer.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VolunteerRole {

    VOLUNTEER("ROLE_VOLUNTEER"), AUTH_VOLUNTEER("ROLE_AUTH_VOLUNTEER");

    private final String key;

}
