package com.pawwithu.connectdog.domain.notification.controller;

import com.pawwithu.connectdog.domain.notification.dto.response.NotificationIntermediaryGetResponse;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationVolunteerGetResponse;
import com.pawwithu.connectdog.domain.notification.service.NotificationService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Notification", description = "Notification API")
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "봉사자 - 알림 목록 조회", description = "이동봉사자의 알림 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "알림 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/volunteers/notifications/my")
    public ResponseEntity<List<NotificationVolunteerGetResponse>> getVolunteerNotification(@AuthenticationPrincipal UserDetails loginUser, Pageable pageable) {
        List<NotificationVolunteerGetResponse> response = notificationService.getVolunteerNotifications(loginUser.getUsername(), pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "모집자 - 알림 목록 조회", description = "모집자의 알림 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "알림 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/intermediaries/notifications/my")
    public ResponseEntity<List<NotificationIntermediaryGetResponse>> getIntermediaryNotification(@AuthenticationPrincipal UserDetails loginUser, Pageable pageable) {
        List<NotificationIntermediaryGetResponse> response = notificationService.getIntermediaryNotification(loginUser.getUsername(), pageable);
        return ResponseEntity.ok(response);
    }

}
