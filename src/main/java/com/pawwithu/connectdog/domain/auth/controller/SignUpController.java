package com.pawwithu.connectdog.domain.auth.controller;

import com.pawwithu.connectdog.domain.auth.dto.request.EmailRequest;
import com.pawwithu.connectdog.domain.auth.dto.request.IntermediarySignUpRequest;
import com.pawwithu.connectdog.domain.auth.dto.request.SocialSignUpRequest;
import com.pawwithu.connectdog.domain.auth.dto.request.VolunteerSignUpRequest;
import com.pawwithu.connectdog.domain.auth.dto.response.EmailResponse;
import com.pawwithu.connectdog.domain.auth.service.AuthService;
import com.pawwithu.connectdog.domain.auth.service.EmailService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Sign-Up", description = "Sign-Up API")
@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final AuthService authService;
    private final EmailService emailService;

    @Operation(summary = "이동봉사자 자체 회원가입", description = "이동봉사자 자체 회원가입을 합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "이동봉사자 자체 회원가입 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 이메일 형식에 맞지 않습니다. \t\n V1, 이메일은 필수 입력 값입니다. \t\n V1, 영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상을 입력해 주세요. \t\n " +
                    "V1, 닉네임은 한글, 숫자만 사용 가능합니다. \t\n V1, 닉네임은 필수 입력 값입니다. \t\n V1, 닉네임은 2~10자로 입력해 주세요. \t\n A1, 이미 등록된 이메일입니다. \t\n A2, 이미 사용 중인 닉네임입니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/volunteers/sign-up")
    public ResponseEntity<Void> volunteerSignUp(@RequestBody @Valid VolunteerSignUpRequest request) {
        authService.volunteerSignUp(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "이동봉사 중개 자체 회원가입", description = "이동봉사 중개 자체 회원가입을 합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "이동봉사 중개 자체 회원가입 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 이메일 형식에 맞지 않습니다. \t\n V1, 이메일은 필수 입력 값입니다. \t\n " +
                    "V1, 영문+숫자 10자 이상 또는 영문+숫자+특수기호 8자 이상을 입력해 주세요. \t\n V1, 이름/단체명은 필수 입력 값입니다. \t\n V1, url 형식을 입력해 주세요. \t\n " +
                    "V1, 한줄 소개는 50자 이하로 입력해 주세요. \t\n A1, 이미 등록된 이메일입니다. \t\n F1, 파일이 존재하지 않습니다. \t\n F2, 파일 업로드에 실패했습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/intermediaries/sign-up", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> intermediarySignUp(@RequestPart @Valid IntermediarySignUpRequest request,
                                                   @RequestPart(name = "authImage", required = false) MultipartFile authImage,
                                                   @RequestPart(name = "profileImage", required = false) MultipartFile profileImage) {
        authService.intermediarySignUp(request, authImage, profileImage);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "이메일 인증번호 전송", description = "입력한 이메일로 인증번호를 전송합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "이메일 인증번호 전송 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 이메일 형식에 맞지 않습니다. \t\n V1, 이메일은 필수 입력 값입니다. \t\n A1, 이미 존재하는 이메일입니다. \t\n A4, 이메일 인증 코드 전송을 실패했습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = {"/volunteers/sign-up/email", "/intermediaries/sign-up/email"})
    public ResponseEntity<EmailResponse> mailConfirm(@RequestBody @Valid EmailRequest request){
        EmailResponse emailResponse = emailService.sendEmail(request);
        return ResponseEntity.ok(emailResponse);
    }

    @Operation(summary = "이동봉사자 소셜 로그인 추가 회원가입", description = "소셜 로그인하는 이동봉사자 추가 회원가입을 합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "204", description = "이동봉사자 소셜 로그인 추가 회원가입 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 닉네임은 한글, 숫자만 사용 가능합니다. \t\n V1, 닉네임은 필수 입력 값입니다. \t\n V1, 닉네임은 2~10자로 입력해 주세요. \t\n A2, 이미 사용 중인 닉네임입니다. \t\n " +
                    "T3, 잘못된 토큰입니다. \t\n M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PatchMapping("/volunteers/sign-up/social")
    public ResponseEntity<Void> volunteerSocialSignUp(@AuthenticationPrincipal UserDetails loginUser, @RequestBody @Valid SocialSignUpRequest socialSignUpRequest) {
        authService.volunteerSocialSignUp(loginUser.getUsername(), socialSignUpRequest);
        return ResponseEntity.noContent().build();
    }

}
