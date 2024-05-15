package com.pawwithu.connectdog.domain.intermediary.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record IntermediaryMyProfileRequest(
        @Pattern(regexp = "^(http|https)://[a-zA-Z0-9-.]+\\.[a-zA-Z]{2,}(/\\S*)?$",
                message = "url 형식을 입력해 주세요.")
        String url,
        @Size(max=50, message = "한줄 소개는 50자 이하로 입력해 주세요.")
        String intro,
        @Size(max=50, message = "문의 받을 연락처는 50자 이하로 입력해 주세요.")
        String contact,
        @Size(max=200, message = "안내사항은 200자 이하로 입력해 주세요.")
        String guide
) {
}
