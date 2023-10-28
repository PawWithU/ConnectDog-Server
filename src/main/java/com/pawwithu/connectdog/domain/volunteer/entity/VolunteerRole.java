package com.pawwithu.connectdog.domain.volunteer.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VolunteerRole {

    USER("ROLE_USER"), AUTH_USER("ROLE_AUTH_USER");

    private final String key;

}
