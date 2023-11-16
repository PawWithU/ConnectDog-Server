package com.pawwithu.connectdog.domain.intermediary.controller;

import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetPostsResponse;
import com.pawwithu.connectdog.domain.intermediary.service.IntermediaryService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Intermediary", description = "Intermediary API")
@RestController
@RequiredArgsConstructor
public class IntermediaryController {

    private final IntermediaryService intermediaryService;

    @Operation(summary = "중개 프로필 - 이동봉사 공고 목록 조회", description = "중개 프로필에서 이동봉사 공고 목록을 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "이동봉사 공고 목록 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/volunteers/intermediaries/{intermediaryId}/posts")
    public ResponseEntity<List<IntermediaryGetPostsResponse>> getIntermediaryPosts(@PathVariable Long intermediaryId,
                                                                                   Pageable pageable) {
        List<IntermediaryGetPostsResponse> response = intermediaryService.getIntermediaryPosts(intermediaryId, pageable);
        return ResponseEntity.ok(response);
    }
}
