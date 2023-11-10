package com.pawwithu.connectdog.domain.bookmark.controller;

import com.pawwithu.connectdog.domain.bookmark.service.BookmarkService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Bookmark", description = "Bookmark API")
@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 등록 및 삭제", description = "북마크를 등록하거나 삭제합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "북마크 등록 및 삭제 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사자를 찾을 수 없습니다. \t\n P2, 해당 공고를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/volunteers/bookmarks/{postId}")
    public ResponseEntity<Void> clickBookmark(@AuthenticationPrincipal UserDetails loginUser, @PathVariable("postId") Long postId) {
        bookmarkService.clickBookmark(loginUser.getUsername(), postId);
        return ResponseEntity.noContent().build();
    }

}
