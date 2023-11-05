package com.pawwithu.connectdog.domain.oauth.controller;

import com.pawwithu.connectdog.domain.oauth.dto.request.SocialLoginRequest;
import com.pawwithu.connectdog.domain.oauth.dto.response.LoginResponse;
import com.pawwithu.connectdog.domain.oauth.service.OAuthService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "OAuth", description = "OAuth API")
@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @Operation(summary = "이동봉사자 소셜 로그인", description = "이동봉사자 소셜 로그인을 합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "이동봉사자 소셜 로그인 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = ""
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/volunteers/login/social")
    public ResponseEntity<LoginResponse> volunteerSocialLogin(@RequestBody @Valid SocialLoginRequest request) {
        LoginResponse response = oAuthService.volunteerSocialLogin(request);
        return ResponseEntity.ok(response);
    }

}