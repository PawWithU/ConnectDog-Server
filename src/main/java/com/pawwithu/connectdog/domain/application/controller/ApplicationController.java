package com.pawwithu.connectdog.domain.application.controller;

import com.pawwithu.connectdog.domain.application.dto.request.VolunteerApplyRequest;
import com.pawwithu.connectdog.domain.application.dto.response.ApplicationWaitingResponse;
import com.pawwithu.connectdog.domain.application.service.ApplicationService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Application", description = "Application API")
@RestController
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "이동봉사 신청", description = "이동봉사를 신청합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "204", description = "이동봉사자 소셜 로그인 추가 회원가입 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 이름은 필수 입력 값입니다. \t\n V1, 휴대전화 번호는 필수 입력 값입니다. \t\n V1, 유효하지 않은 휴대전화 번호입니다. \t\n " +
                    "V1, 교통수단은 필수 입력 값입니다. \t\n V1, 10~200자로 입력해 주세요. \t\n M1, 해당 이동봉사자를 찾을 수 없습니다. \t\n " +
                    "P2, 해당 공고를 찾을 수 없습니다. \t\n AP1, 이미 신청된 공고입니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/volunteers/posts/{postId}/applications")
    public ResponseEntity<Void> volunteerApply(@AuthenticationPrincipal UserDetails loginUser,
                                               @PathVariable Long postId,
                                               @RequestBody @Valid VolunteerApplyRequest request) {
        applicationService.volunteerApply(loginUser.getUsername(), postId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "봉사 관리 - 승인 대기중", description = "이동봉사 승인 대기중 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사 승인 대기중 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/volunteers/applications/waiting")
    public ResponseEntity<List<ApplicationWaitingResponse>> getWaitingApplications(@AuthenticationPrincipal UserDetails loginUser,
                                                                           Pageable pageable) {
        List<ApplicationWaitingResponse> response = applicationService.getWaitingApplications(loginUser.getUsername(), pageable);
        return ResponseEntity.ok(response);
    }
}
