package com.pawwithu.connectdog.domain.volunteer.dto.request;

import jakarta.validation.constraints.Pattern;

public record VolunteerPasswordRequest(@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{10,}$|^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=?]).{8,}$",
                                        message = "영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상을 입력해 주세요.")
                                       String password) {
}
