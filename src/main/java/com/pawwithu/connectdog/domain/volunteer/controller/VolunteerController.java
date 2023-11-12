package com.pawwithu.connectdog.domain.volunteer.controller;

import com.pawwithu.connectdog.domain.volunteer.dto.request.AdditionalAuthRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.NicknameRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.response.NicknameResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
