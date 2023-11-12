package com.pawwithu.connectdog.domain.review.controller;

import com.pawwithu.connectdog.domain.review.dto.request.ReviewCreateRequest;
import com.pawwithu.connectdog.domain.review.service.ReviewService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Review", description = "Review API")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "후기 등록", description = "후기를 등록합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "후기 등록 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "F1, 파일이 존재하지 않습니다. \t\n F2, 파일 업로드에 실패했습니다. \t\n M2, 해당 이동봉사 중개를 찾을 수 없습니다. \t\n P2, 해당 공고를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/volunteers/reviews/{postId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createReview(@AuthenticationPrincipal UserDetails loginUser, @PathVariable("postId") Long postId,
                                           @RequestPart @Valid ReviewCreateRequest request,
                                           @RequestPart(name = "files", required = false) List<MultipartFile> files) {
        reviewService.createReview(loginUser.getUsername(), postId, request, files);
        return ResponseEntity.noContent().build();
    }



}
