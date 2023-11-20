package com.pawwithu.connectdog.domain.fcm.controller;

import com.pawwithu.connectdog.domain.fcm.dto.request.FcmTokenRequest;
import com.pawwithu.connectdog.domain.fcm.dto.request.IntermediaryFcmRequest;
import com.pawwithu.connectdog.domain.fcm.dto.request.VolunteerFcmRequest;
import com.pawwithu.connectdog.domain.fcm.service.FcmService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.pawwithu.connectdog.domain.fcm.dto.NotificationMessage.APPLICATION;

@Tag(name = "Fcm", description = "Fcm API")
@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @Operation(summary = "이동봉사자 - FCM 토큰 저장", description = "이동봉사자의 FCM 토큰을 저장합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "FCM 토큰 저장 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, fcm 토큰은 필수 입력 값입니다. \t\n M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/volunteers/fcm")
    public ResponseEntity<Void> saveVolunteerFcm(@AuthenticationPrincipal UserDetails loginUser,
                                        @Valid @RequestBody VolunteerFcmRequest request) {

        fcmService.saveVolunteerFcm(loginUser.getUsername(), request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "이동봉사 중개 - FCM 토큰 저장", description = "이동봉사 중개의 FCM 토큰을 저장합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "FCM 토큰 저장 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, fcm 토큰은 필수 입력 값입니다. \t\n M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/intermediaries/fcm")
    public ResponseEntity<Void> saveIntermediaryFcm(@AuthenticationPrincipal UserDetails loginUser,
                                        @Valid @RequestBody IntermediaryFcmRequest request) {

        fcmService.saveIntermediaryFcm(loginUser.getUsername(), request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "FCM 토큰 테스트", description = "FCM 토큰을 테스트 합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "FCM 토큰 테스트 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, fcm 토큰은 필수 입력 값입니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping("/fcm-test")
    public ResponseEntity<Void> testFcmToken(@Valid @RequestBody FcmTokenRequest request) {
        fcmService.sendMessageTo(request.fcmToken(), APPLICATION.getTitleWithLoc("서울 강남구", "서울 도봉구"), APPLICATION.getBodyWithName("포윗유"));
        return ResponseEntity.noContent().build();
    }

}
