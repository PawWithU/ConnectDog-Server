package com.pawwithu.connectdog.domain.application.dto.request;

import com.pawwithu.connectdog.domain.application.entity.Application;
import com.pawwithu.connectdog.domain.application.entity.ApplicationStatus;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VolunteerApplyRequest(@NotBlank(message = "이름은 필수 입력 값입니다.")
                                    @Pattern(regexp = "^[가-힣]*$", message = "한글만 입력 가능합니다.")
                                    String name,
                                    @NotBlank(message = "휴대전화 번호는 필수 입력 값입니다.")
                                    @Pattern(regexp = "^010[0-9]{8}$", message = "유효하지 않은 휴대전화 번호입니다.")
                                    String phone,
                                    @Size(max=100, message = "100자 이내로 입력해 주세요.")
                                    String content) {

    public Application toEntity(Post post, Intermediary intermediary, Volunteer volunteer) {
        return Application.builder()
                .status(ApplicationStatus.WAITING)
                .volunteerName(name)
                .phone(phone)
                .content(content)
                .post(post)
                .intermediary(intermediary)
                .volunteer(volunteer)
                .build();
    }
}
