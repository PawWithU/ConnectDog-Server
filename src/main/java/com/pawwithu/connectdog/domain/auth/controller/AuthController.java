package com.pawwithu.connectdog.domain.auth.controller;

import com.pawwithu.connectdog.domain.auth.dto.request.RefreshTokenRequest;
import com.pawwithu.connectdog.domain.oauth.dto.response.LoginResponse;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import com.pawwithu.connectdog.jwt.service.JwtService;
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

@Tag(name = "Auth", description = "Auth API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @Operation(summary = "토큰 재발행", description = "AccessToken, RefreshToken 재발행 합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "토큰 재발행 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 이메일은 필수 전송 값입니다. \t\n T3, 잘못된 토큰입니다. \t\n T5, 해당 RefreshToken을 Redis에서 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/reissue-token")
    public ResponseEntity<LoginResponse> reIssueToken(@Valid @RequestBody RefreshTokenRequest request) {

        String refreshToken = request.refreshToken();
        LoginResponse response = jwtService.reIssueToken(refreshToken);
        return ResponseEntity.ok(response);
    }

}
