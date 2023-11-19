package com.pawwithu.connectdog.domain.volunteer.controller;

import com.pawwithu.connectdog.domain.volunteer.dto.request.AdditionalAuthRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.NicknameRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.VolunteerMyProfileRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.response.NicknameResponse;
import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBadgeResponse;
import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBookmarkResponse;
import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyInfoResponse;
import com.pawwithu.connectdog.domain.volunteer.service.VolunteerService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Volunteer", description = "Volunteer API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @Operation(summary = "닉네임 중복 여부 확인", description = "닉네임 중복 여부를 확인합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "닉네임 중복 여부 확인 성공")
                    , @ApiResponse(responseCode = "400", description = "V1, 닉네임은 한글, 숫자만 사용가능합니다. \t\n V1, 닉네임은 필수 입력 값입니다. \t\n V1, 닉네임은 2~10자로 입력해 주세요.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/nickname/isDuplicated")
    public ResponseEntity<NicknameResponse> isNicknameDuplicated(@RequestBody @Valid NicknameRequest request) {
        NicknameResponse response = volunteerService.isNicknameDuplicated(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "이름, 전화번호 추가 인증", description = "이름과 전화번호를 추가로 인증합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {
                    @ApiResponse(responseCode = "204", description = "이름, 전화번호 추가 인증 성공")
                    , @ApiResponse(responseCode = "400", description = "V1, 이름은 필수 입력 값입니다. \t\n V1, 휴대전화 번호는 필수 입력 값입니다. \t\n V1, 유효하지 않은 휴대전화 번호입니다.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping("/additional-auth")
    public ResponseEntity<Void> additionalAuth(@AuthenticationPrincipal UserDetails loginUser,
                                               @RequestBody @Valid AdditionalAuthRequest request) {
        volunteerService.additionalAuth(loginUser.getUsername(), request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "마이페이지 통계 정보 조회 API", description = "마이페이지 통계 정보를 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "마이페이지 통계 정보 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/my/info")
    public ResponseEntity<VolunteerGetMyInfoResponse> getMyInfo(@AuthenticationPrincipal UserDetails loginUser) {
        VolunteerGetMyInfoResponse response = volunteerService.getMyInfo(loginUser.getUsername());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 북마크한 공고 목록 조회 API", description = "마이페이지 북마크한 공고 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "마이페이지 북마크한 공고 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/my/bookmarks")
    public ResponseEntity<List<VolunteerGetMyBookmarkResponse>> getMyBookmarks(@AuthenticationPrincipal UserDetails loginUser) {
        List<VolunteerGetMyBookmarkResponse> response = volunteerService.getMyBookmarks(loginUser.getUsername());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 활동 배지 목록 조회 API", description = "마이페이지 활동 배지 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "마이페이지 활동 배지 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/my/badges")
    public ResponseEntity<List<VolunteerGetMyBadgeResponse>> getMyBadges(@AuthenticationPrincipal UserDetails loginUser) {
        List<VolunteerGetMyBadgeResponse> response = volunteerService.getMyBadges(loginUser.getUsername());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 프로필 수정", description = "마이페이지 프로필을 수정합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "204", description = "마이페이지 프로필 수정 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 닉네임은 한글, 숫자만 사용 가능합니다. \t\n V1, 닉네임은 필수 입력 값입니다. \t\n V1, 닉네임은 2~10자로 입력해 주세요. \t\n A2, 이미 사용 중인 닉네임입니다. \t\n  M1, 해당 이동봉사자를 찾을 수 없습니다. \t\n " +
                    "T3, 잘못된 토큰입니다. \t\n M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PatchMapping("/my/profile")
    public ResponseEntity<Void> volunteerMyProfile(@AuthenticationPrincipal UserDetails loginUser, @RequestBody @Valid VolunteerMyProfileRequest volunteerMyProfileRequest) {
        volunteerService.volunteerMyProfile(loginUser.getUsername(), volunteerMyProfileRequest);
        return ResponseEntity.noContent().build();
    }

}
