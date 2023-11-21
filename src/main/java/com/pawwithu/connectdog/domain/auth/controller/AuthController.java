package com.pawwithu.connectdog.domain.auth.controller;

import com.pawwithu.connectdog.domain.auth.dto.request.RefreshTokenRequest;
import com.pawwithu.connectdog.domain.auth.service.AuthService;
import com.pawwithu.connectdog.domain.oauth.dto.response.LoginResponse;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import com.pawwithu.connectdog.jwt.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Auth API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;

    @Operation(summary = "토큰 재발행", description = "AccessToken, RefreshToken 재발행 합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "토큰 재발행 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, RefreshToken은 필수 입력 값입니다. \t\n T3, 잘못된 토큰입니다. \t\n T5, 해당 RefreshToken을 Redis에서 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/reissue-token")
    public ResponseEntity<LoginResponse> reIssueToken(@Valid @RequestBody RefreshTokenRequest request) {

        String refreshToken = request.refreshToken();
        LoginResponse response = jwtService.reIssueToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "이동봉사자 - 로그아웃", description = "이동봉사자가 로그아웃을 합니다.",
            security = {@SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사자 로그아웃 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "T1, 토큰이 존재하지 않습니다. \t\n M1, 해당 이동봉사자를 찾을 수 없습니다. \t\n M4, 해당 토큰에서 ROLE_NAME을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping("/volunteers/logout")
    public ResponseEntity<Void> volunteersLogout(HttpServletRequest request, @AuthenticationPrincipal UserDetails loginUser) {
        authService.volunteersLogout(request, loginUser.getUsername());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 합니다.",
            security = {@SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "200", description = "로그아웃 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "T1, 토큰이 존재하지 않습니다. \t\n M2, 해당 이동봉사 중개를 찾을 수 없습니다. \t\n M4, 해당 토큰에서 ROLE_NAME을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping("/intermediaries/logout")
    public ResponseEntity<Void> intermediariesLogout(HttpServletRequest request, @AuthenticationPrincipal UserDetails loginUser) {
        authService.intermediariesLogout(request, loginUser.getUsername());
        return ResponseEntity.noContent().build();
    }

}
