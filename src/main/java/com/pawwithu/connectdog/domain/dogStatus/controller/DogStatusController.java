package com.pawwithu.connectdog.domain.dogStatus.controller;

import com.pawwithu.connectdog.domain.dogStatus.dto.request.DogStatusCreateRequest;
import com.pawwithu.connectdog.domain.dogStatus.service.DogStatusService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "DogStatus", description = "DogStatus API")
@RestController
@RequiredArgsConstructor
public class DogStatusController {

    private final DogStatusService dogStatusService;

    @Operation(summary = "근황 등록", description = "근황을 등록합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "204", description = "근황 등록 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 근황 내용은 필수 입력 값입니다. \t\n V1, 내용은 20~300자로 입력해 주세요. \t\n F1, 파일이 존재하지 않습니다. \t\n F2, 파일 업로드에 실패했습니다. \t\n " +
                    "M2, 해당 이동봉사 중개를 찾을 수 없습니다. \t\n P2, 해당 공고를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/intermediaries/posts/{postId}/dogStatus", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createReview(@AuthenticationPrincipal UserDetails loginUser, @PathVariable("postId") Long postId,
                                             @RequestPart @Valid DogStatusCreateRequest request,
                                             @RequestPart(name = "files", required = false) List<MultipartFile> files) {
        dogStatusService.createDogStatus(loginUser.getUsername(), postId, request, files);
        return ResponseEntity.noContent().build();
    }
}
