package com.pawwithu.connectdog.domain.dogStatus.dto.request;

import com.pawwithu.connectdog.domain.dogStatus.entity.DogStatus;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DogStatusCreateRequest(@NotBlank(message = "근황 내용은 필수 입력 값입니다.")
                                     @Size(min=20, max=300, message = "내용은 20~300자로 입력해 주세요.")
                                     String content) {

    public static DogStatus dogStatusToEntity(DogStatusCreateRequest request, Intermediary intermediary, Post post) {
        return DogStatus.builder()
                .content(request.content)
                .intermediary(intermediary)
                .post(post)
                .build();
    }
}
