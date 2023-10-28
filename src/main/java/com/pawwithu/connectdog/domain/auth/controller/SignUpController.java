package com.pawwithu.connectdog.domain.auth.controller;

import com.pawwithu.connectdog.domain.auth.dto.request.EmailRequest;
import com.pawwithu.connectdog.domain.auth.dto.request.VolunteerSignUpRequest;
import com.pawwithu.connectdog.domain.auth.dto.response.EmailResponse;
import com.pawwithu.connectdog.domain.auth.service.AuthService;
import com.pawwithu.connectdog.domain.auth.service.EmailService;
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

@Tag(name = "Sign-Up", description = "Sign-Up API")
@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final AuthService authService;
    private final EmailService emailService;

    @Operation(summary = "이동봉사자 자체 회원가입", description = "이동봉사자 자체 회원가입을 합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "이동봉사자 자체 회원가입 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 이메일 형식에 맞지 않습니다. \t\n V1, 이메일은 필수 입력 값입니다. \t\n V1, 영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상을 입력해 주세요." +
                    "\t\n V1, 닉네임은 한글, 숫자만 사용가능합니다. \t\n V1, 닉네임은 필수 입력 값입니다. \t\n V1, 2~10자의 닉네임이어야 합니다 \t\n A1, 이미 존재하는 이메일입니다. \t\n A2, 이미 존재하는 사용자 닉네임입니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/volunteers/sign-up")
    public ResponseEntity<Void> volunteerSignUp(@RequestBody @Valid VolunteerSignUpRequest volunteerSignUpRequest) {
        authService.volunteerSignUp(volunteerSignUpRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "이메일 인증번호 전송", description = "입력한 이메일로 인증번호를 전송합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "이메일 인증번호 전송 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 이메일 형식에 맞지 않습니다. \t\n V1, 이메일은 필수 입력 값입니다. \t\n A1, 이미 존재하는 이메일입니다. \t\n A4, 이메일 인증 코드 전송을 실패했습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = {"/volunteers/sign-up/email", "/intermediaries/sign-up/email"})
    public ResponseEntity<EmailResponse> mailConfirm(@RequestBody @Valid EmailRequest emailRequest){
        EmailResponse emailResponse = emailService.sendEmail(emailRequest);
        return ResponseEntity.ok(emailResponse);
    }

}
