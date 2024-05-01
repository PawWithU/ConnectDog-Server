package com.pawwithu.connectdog.domain.auth.dto.request;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.entity.IntermediaryRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record IntermediarySignUpRequest(Boolean isOptionAgr,
                                        @NotBlank(message = "이름은 필수 입력 값입니다.")
                                        String realName,
                                        @NotBlank(message = "휴대전화 번호는 필수 입력 값입니다.")
                                        @Pattern(regexp = "^010[0-9]{8}$", message = "유효하지 않은 휴대전화 번호입니다.")
                                        String phone,
                                        @Email(message="이메일 형식에 맞지 않습니다.")
                                        @NotBlank(message = "이메일은 필수 입력 값입니다.")
                                        String email,
                                        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{10,}$|^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=?]).{8,}$",
                                                message = "영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상을 입력해 주세요.")
                                        String password,
                                        @NotBlank(message = "모집자명은 필수 입력 값입니다.")
                                        String name,
                                        @Size(max=50, message = "한줄 소개는 50자 이하로 입력해 주세요.")
                                        String intro,
                                        @Pattern(regexp = "^(http|https)://[a-zA-Z0-9-.]+\\.[a-zA-Z]{2,}(/\\S*)?$",
                                                message = "url 형식을 입력해 주세요.")
                                        String url,
                                        String contact) {

    public static Intermediary toEntity(IntermediarySignUpRequest request, String authImage, String profileImage) {
        return Intermediary.builder()
                .isOptionAgr(request.isOptionAgr)
                .realName(request.realName)
                .phone(request.phone)
                .email(request.email)
                .password(request.password)
                .name(request.name)
                .url(request.url)
                .authImage(authImage)
                .profileImage(profileImage)
                .intro(request.intro)
                .contact(request.contact)
                .role(IntermediaryRole.INTERMEDIARY)
                .notification(true)
                .build();
    }
}
