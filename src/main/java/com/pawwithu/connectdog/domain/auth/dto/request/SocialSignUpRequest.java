package com.pawwithu.connectdog.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SocialSignUpRequest(
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        String name,
        @NotBlank(message = "휴대전화 번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^010[0-9]{8}$", message = "유효하지 않은 휴대전화 번호입니다.")
        String phone,
        @Pattern(regexp = "^[가-힣0-9]*$", message = "닉네임은 한글, 숫자만 사용 가능합니다.")
        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Size(min=2, max=10, message = "닉네임은 2~10자로 입력해 주세요.")
        String nickname,
        Integer profileImageNum,
        Boolean isOptionAgr) {
}
