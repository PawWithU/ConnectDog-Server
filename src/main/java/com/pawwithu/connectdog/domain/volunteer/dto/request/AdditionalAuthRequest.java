package com.pawwithu.connectdog.domain.volunteer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AdditionalAuthRequest(@NotBlank(message = "이름은 필수 입력 값입니다.")
                                    String name,
                                    @NotBlank(message = "휴대전화 번호는 필수 입력 값입니다.")
                                    @Pattern(regexp = "^010[0-9]{8}$", message = "유효하지 않은 휴대전화 번호입니다.")
                                    String phone) {
}
