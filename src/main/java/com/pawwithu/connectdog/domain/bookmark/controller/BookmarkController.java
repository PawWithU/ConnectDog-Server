package com.pawwithu.connectdog.domain.bookmark.controller;

import com.pawwithu.connectdog.domain.bookmark.service.BookmarkService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Bookmark", description = "Bookmark API")
@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 등록", description = "북마크를 등록합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "204", description = "북마크 등록 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사자를 찾을 수 없습니다. \t\n P2, 해당 공고를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/volunteers/bookmarks/{postId}")
    public ResponseEntity<Void> createBookmark(@AuthenticationPrincipal UserDetails loginUser, @PathVariable("postId") Long postId) {
        bookmarkService.createBookmark(loginUser.getUsername(), postId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "북마크 삭제", description = "북마크를 삭제합니다.",
            security = { @SecurityRequirement(name = "bearer-key") },
            responses = {@ApiResponse(responseCode = "204", description = "북마크 삭제 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M2, 해당 이동봉사자를 찾을 수 없습니다. \t\n P2, 해당 공고를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @DeleteMapping(value = "/volunteers/bookmarks/{postId}")
    public ResponseEntity<Void> deleteBookmark(@AuthenticationPrincipal UserDetails loginUser, @PathVariable("postId") Long postId) {
        bookmarkService.deleteBookmark(loginUser.getUsername(), postId);
        return ResponseEntity.noContent().build();
    }
}
