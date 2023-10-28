package com.pawwithu.connectdog.domain.intermediary.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IntermediaryRole {

    INTERMEDIARY("ROLE_INTERMEDIARY"), AUTH_INTERMEDIARY("ROLE_AUTH_INTERMEDIARY");

    private final String key;
}
