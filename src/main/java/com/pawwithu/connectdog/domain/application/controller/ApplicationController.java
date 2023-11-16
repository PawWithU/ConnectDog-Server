package com.pawwithu.connectdog.domain.application.controller;

import com.pawwithu.connectdog.domain.application.dto.request.VolunteerApplyRequest;
import com.pawwithu.connectdog.domain.application.dto.response.*;
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
    public ResponseEntity<List<ApplicationVolunteerWaitingResponse>> getVolunteerWaitingApplications(@AuthenticationPrincipal UserDetails loginUser,
                                                                                                     Pageable pageable) {
        List<ApplicationVolunteerWaitingResponse> response = applicationService.getVolunteerWaitingApplications(loginUser.getUsername(), pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "봉사 관리 - 진행중", description = "이동봉사 진행중 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사 진행중 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/volunteers/applications/progressing")
    public ResponseEntity<List<ApplicationVolunteerProgressingResponse>> getVolunteerProgressingApplications(@AuthenticationPrincipal UserDetails loginUser,
                                                                                                             Pageable pageable) {
        List<ApplicationVolunteerProgressingResponse> response = applicationService.getVolunteerProgressingApplications(loginUser.getUsername(), pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "봉사 관리 - 승인 대기중 - 내 신청 내역 단건 조회", description = "승인 대기중인 이동봉사 목록에서 내 신청 내역을 단건 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "신청 내역 단건 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사자를 찾을 수 없습니다. \t\n AP2, 해당 신청 내역을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/volunteers/applications/{applicationId}")
    public ResponseEntity<ApplicationVolunteerGetOneResponse> getVolunteerOneApplication(@AuthenticationPrincipal UserDetails loginUser,
                                                                                         @PathVariable Long applicationId) {
        ApplicationVolunteerGetOneResponse response = applicationService.getVolunteerOneApplication(loginUser.getUsername(), applicationId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "봉사 관리 - 승인 대기중 - 내 신청 내역 - 봉사 신청 취소", description = "내 신청 내역에서 봉사 신청을 취소합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "봉사 신청 취소 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사자를 찾을 수 없습니다. \t\n AP2, 해당 신청 내역을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping( "/volunteers/applications/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@AuthenticationPrincipal UserDetails loginUser,
                                                  @PathVariable Long applicationId) {
        applicationService.deleteApplication(loginUser.getUsername(), applicationId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "봉사 관리 - 승인 대기중 - 이동봉사자 확인 - 봉사 신청 확정", description = "이동봉사자의 봉사 신청을 확정합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "봉사 신청 확정 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다. \t\n AP2, 해당 신청 내역을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PatchMapping( "/intermediaries/applications/{applicationId}")
    public ResponseEntity<Void> confirmApplication(@AuthenticationPrincipal UserDetails loginUser,
                                                  @PathVariable Long applicationId) {
        applicationService.confirmApplication(loginUser.getUsername(), applicationId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "봉사 관리 - 승인 대기중 - 이동봉사자 확인 - 봉사 신청 반려", description = "이동봉사자의 봉사 신청을 반려합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "봉사 신청 반려 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다. \t\n AP2, 해당 신청 내역을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping( "/intermediaries/applications/{applicationId}")
    public ResponseEntity<Void> cancelApplication(@AuthenticationPrincipal UserDetails loginUser,
                                                   @PathVariable Long applicationId) {
        applicationService.cancelApplication(loginUser.getUsername(), applicationId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "봉사 관리 - 승인 대기중 목록 조회", description = "이동봉사 승인 대기중 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사 승인 대기중 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/intermediaries/applications/waiting")
    public ResponseEntity<List<ApplicationIntermediaryWaitingResponse>> getIntermediaryWaitingApplications(@AuthenticationPrincipal UserDetails loginUser,
                                                                                                           Pageable pageable) {
        List<ApplicationIntermediaryWaitingResponse> response = applicationService.getIntermediaryWaitingApplications(loginUser.getUsername(), pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "봉사 관리 - 진행중 목록 조회", description = "이동봉사 진행중 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사 진행중 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/intermediaries/applications/progressing")
    public ResponseEntity<List<ApplicationIntermediaryProgressingResponse>> getIntermediaryProgressingApplications(@AuthenticationPrincipal UserDetails loginUser,
                                                                                                                   Pageable pageable) {
        List<ApplicationIntermediaryProgressingResponse> response = applicationService.getIntermediaryProgressingApplications(loginUser.getUsername(), pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "봉사 관리 - 봉사 완료 목록 조회", description = "이동봉사 완료 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사 완료 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/volunteers/applications/completed")
    public ResponseEntity<List<ApplicationVolunteerCompletedResponse>> getVolunteerCompletedApplications(@AuthenticationPrincipal UserDetails loginUser,
                                                                                                                   Pageable pageable) {
        List<ApplicationVolunteerCompletedResponse> response = applicationService.getVolunteerCompletedApplications(loginUser.getUsername(), pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "봉사 관리 - 봉사 완료 목록 조회", description = "이동봉사 완료 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사 완료 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/intermediaries/applications/completed")
    public ResponseEntity<List<ApplicationIntermediaryCompletedResponse>> getIntermediaryCompletedApplications(@AuthenticationPrincipal UserDetails loginUser,
                                                                                                                   Pageable pageable) {
        List<ApplicationIntermediaryCompletedResponse> response = applicationService.getIntermediaryCompletedApplications(loginUser.getUsername(), pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "봉사 관리 - 승인 대기중 - 이동봉사자 확인", description = "승인 대기중인 이동봉사 목록에서 이동봉사자를 확인합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사자 확인 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다. \t\n AP2, 해당 신청 내역을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/intermediaries/applications/{applicationId}")
    public ResponseEntity<ApplicationIntermediaryGetOneResponse> getIntermediaryOneApplication(@AuthenticationPrincipal UserDetails loginUser,
                                                                                               @PathVariable Long applicationId) {
        ApplicationIntermediaryGetOneResponse response = applicationService.getIntermediaryOneApplication(loginUser.getUsername(), applicationId);
        return ResponseEntity.ok(response);
    }
}
