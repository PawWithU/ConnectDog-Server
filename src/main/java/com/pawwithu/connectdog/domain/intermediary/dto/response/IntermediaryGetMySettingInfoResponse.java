package com.pawwithu.connectdog.domain.intermediary.dto.response;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;

public record IntermediaryGetMySettingInfoResponse(String realName, String phone, String email) {
    public static IntermediaryGetMySettingInfoResponse from(Intermediary intermediary) {
        return new IntermediaryGetMySettingInfoResponse(intermediary.getRealName(), intermediary.getPhone(), intermediary.getEmail());
    }
}
