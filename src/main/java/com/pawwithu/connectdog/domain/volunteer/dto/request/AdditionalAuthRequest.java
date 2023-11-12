package com.pawwithu.connectdog.domain.volunteer.dto.request;

import jakarta.validation.constraints.Pattern;

public record AdditionalAuthRequest(String name,
                                    @Pattern(regexp = "^010[0-9]{8}$", message = "유효하지 않은 휴대전화 번호입니다.")
                                    String phone) {
}
