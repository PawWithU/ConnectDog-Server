package com.pawwithu.connectdog.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record IntermediaryNameRequest(@NotBlank(message = "모집자명은 필수 입력 값입니다.")
                                      String name) {
}