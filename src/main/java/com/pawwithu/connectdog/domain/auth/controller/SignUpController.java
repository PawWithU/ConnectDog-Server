package com.pawwithu.connectdog.domain.auth.controller;

import com.pawwithu.connectdog.domain.auth.dto.request.SignUpRequest;
import com.pawwithu.connectdog.domain.auth.service.AuthService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Sign-Up", description = "Sign-Up API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sign-up")
public class SignUpController {

    private final AuthService authService;

    @Operation(summary = "자체 회원가입", description = "이메일을 사용해 회원가입을 합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "자체 회원가입 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "1. 이미 존재하는 이메일입니다. \t\n 2. 이미 존재하는 사용자 닉네임입니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return ResponseEntity.noContent().build();
    }

}
