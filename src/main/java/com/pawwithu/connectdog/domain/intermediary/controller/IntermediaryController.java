package com.pawwithu.connectdog.domain.intermediary.controller;

import com.pawwithu.connectdog.domain.intermediary.dto.request.IntermediaryMyProfileRequest;
import com.pawwithu.connectdog.domain.intermediary.dto.response.*;
import com.pawwithu.connectdog.domain.intermediary.service.IntermediaryService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Intermediary", description = "Intermediary API")
@RestController
@RequiredArgsConstructor
public class IntermediaryController {

    private final IntermediaryService intermediaryService;

    @Operation(summary = "중개 프로필 - 이동봉사 공고 목록 조회", description = "중개 프로필에서 이동봉사 공고 목록을 조회합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사 공고 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/volunteers/intermediaries/{intermediaryId}/posts")
    public ResponseEntity<List<IntermediaryGetPostsResponse>> getIntermediaryPosts(@PathVariable Long intermediaryId,
                                                                                   Pageable pageable) {
        List<IntermediaryGetPostsResponse> response = intermediaryService.getIntermediaryPosts(intermediaryId, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "중개 프로필 - 기본 정보 조회", description = "중개 프로필에서 기본 정보를 조회합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "200", description = "중개 프로필 기본 정보 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/volunteers/intermediaries/{intermediaryId}")
    public ResponseEntity<IntermediaryGetInfoResponse> getIntermediaryInfo(@PathVariable Long intermediaryId) {
        IntermediaryGetInfoResponse response = intermediaryService.getIntermediaryInfo(intermediaryId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "중개 프로필 - 후기 조회", description = "중개 프로필에서 후기를 조회합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "200", description = "중개 프로필 후기 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/volunteers/intermediaries/{intermediaryId}/reviews")
    public ResponseEntity<List<IntermediaryGetReviewsResponse>> getIntermediaryReviews(@PathVariable Long intermediaryId,
                                                                                       Pageable pageable) {
        List<IntermediaryGetReviewsResponse> response = intermediaryService.getIntermediaryReviews(intermediaryId, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "중개 프로필 - 근황 조회", description = "중개 프로필에서 근황을 조회합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "200", description = "중개 프로필 근황 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/volunteers/intermediaries/{intermediaryId}/dogStatuses")
    public ResponseEntity<List<IntermediaryGetDogStatusesResponse>> getIntermediaryDogStatuses(@PathVariable Long intermediaryId,
                                                                       Pageable pageable) {
        List<IntermediaryGetDogStatusesResponse> response = intermediaryService.getIntermediaryDogStatuses(intermediaryId, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "이동봉사 중개 - 홈 화면 정보 조회", description = "홈 화면 정보를 조회합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사 중개 홈 화면 정보 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/intermediaries/home")
    public ResponseEntity<IntermediaryGetHomeResponse> getIntermediaryHome(@AuthenticationPrincipal UserDetails loginUser) {
        IntermediaryGetHomeResponse response = intermediaryService.getIntermediaryHome(loginUser.getUsername());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "이동봉사 중개 - 마이페이지 프로필 수정", description = "마이페이지 프로필을 수정합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "204", description = "마이페이지 프로필 수정 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 닉네임은 한글, 숫자만 사용 가능합니다. \t\n V1, 닉네임은 필수 입력 값입니다. \t\n V1, 닉네임은 2~10자로 입력해 주세요. \t\n A2, 이미 사용 중인 닉네임입니다. \t\n M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PatchMapping("/intermediaries/my/profile")
    public ResponseEntity<Void> intermediaryMyProfile(@AuthenticationPrincipal UserDetails loginUser,
                                                      @RequestPart @Valid IntermediaryMyProfileRequest request,
                                                      @RequestPart(name = "profileImage", required = false) MultipartFile profileImage) {
        intermediaryService.intermediaryMyProfile(loginUser.getUsername(), request, profileImage);
        return ResponseEntity.noContent().build();
    }

}
