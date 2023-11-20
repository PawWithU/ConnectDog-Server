package com.pawwithu.connectdog.domain.fcm.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationMessage {
    APPLICATION("", "님이 이동봉사를 신청하셨어요. 지금 확인해 보세요!");

    private final String title;
    private final String body;

    public String getTitleWithLoc(String departureLoc, String arrivalLoc) {
        return departureLoc + "→" + arrivalLoc;
    }

    public String getBodyWithName(String name) {
        return name + body;
    }

}