package com.pawwithu.connectdog.domain.auth.dto.request;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.entity.IntermediaryRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record IntermediarySignUpRequest(@Email(message="이메일 형식에 맞지 않습니다.")
                                        @NotBlank(message = "이메일은 필수 입력 값입니다.") String email,
                                        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{10,}$|^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=?]).{8,}$",
                                                message = "영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상을 입력해 주세요.") String password,
                                        @NotBlank(message = "이름/단체명은 필수 입력 값입니다.")
                                        String name,
                                        @Pattern(regexp = "^(http|https)://[a-zA-Z0-9-.]+\\.[a-zA-Z]{2,}(/\\S*)?$",
                                                message = "url 형식을 입력해 주세요.") String url,
                                        @Size(max=50, message = "50자 이하의 한줄 소개를 입력해 주세요.")
                                        String intro,
                                        Boolean isOptionAgr) {

    public static Intermediary toEntity(IntermediarySignUpRequest request, String authImage, String profileImage) {
        return Intermediary.builder()
                .email(request.email)
                .password(request.password)
                .name(request.name)
                .url(request.url)
                .authImage(authImage)
                .profileImage(profileImage)
                .intro(request.intro)
                .isOptionAgr(request.isOptionAgr)
                .role(IntermediaryRole.INTERMEDIARY)
                .notification(true)
                .build();
    }
}
