package com.pawwithu.connectdog.domain.auth.dto.request;

import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.entity.VolunteerRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VolunteerSignUpRequest(@Email(message="이메일 형식에 맞지 않습니다.")
                                     @NotBlank(message = "이메일은 필수 입력 값입니다.") String email,
                                     @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{10,}$|^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=?]).{8,}$",
                                             message = "영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상을 입력해 주세요.") String password,
                                     @Pattern(regexp = "^[가-힣0-9]*$", message = "닉네임은 한글, 숫자만 사용 가능합니다.")
                                     @NotBlank(message = "닉네임은 필수 입력 값입니다.")
                                     @Size(min=2, max=10, message = "닉네임은 2~10자로 입력해 주세요.")
                                     String nickname,
                                     Integer profileImageNum,
                                     Boolean isOptionAgr) {

    public Volunteer toEntity() {
        return Volunteer.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .profileImageNum(profileImageNum)
                .isOptionAgr(isOptionAgr)
                .notification(true)
                .role(VolunteerRole.VOLUNTEER)
                .build();
    }
}
