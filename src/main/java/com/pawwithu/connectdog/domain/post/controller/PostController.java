package com.pawwithu.connectdog.domain.post.controller;

import com.pawwithu.connectdog.domain.post.dto.request.PostCreateRequest;
import com.pawwithu.connectdog.domain.post.dto.request.PostSearchRequest;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetHomeResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetOneResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostSearchResponse;
import com.pawwithu.connectdog.domain.post.service.PostService;
import com.pawwithu.connectdog.error.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Post", description = "Post API")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "공고 등록", description = "공고를 등록합니다.",
            responses = {@ApiResponse(responseCode = "204", description = "공고 등록 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "V1, 출발 지역은 필수 입력 값입니다. \t\n V1, 도착 지역은 필수 입력 값입니다. \t\n " +
                    "V1, 이동봉사가 필요한 날짜는 필수 입력 값입니다. \t\n V1, 켄넬 제공 여부는 필수 입력 값입니다. \t\n V1, 이동봉사에 대한 설명은 필수 입력 값입니다. \t\n " +
                    "V1, 강아지 이름은 필수 입력 값입니다. \t\n V1, 강아지 사이즈는 필수 입력 값입니다. \t\n V1, 강아지 성별은 필수 입력 값입니다. \t\n " +
                    "F1, 파일이 존재하지 않습니다. \t\n F2, 파일 업로드에 실패했습니다. \t\n M2, 해당 이동봉사 중개를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/intermediaries/posts", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> createPost(@AuthenticationPrincipal UserDetails loginUser,
                                           @RequestPart @Valid PostCreateRequest request,
                                           @RequestPart(name = "files", required = false) List<MultipartFile> files) {
        postService.createPost(loginUser.getUsername(), request, files);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "홈 화면 공고 5개 최신 순 조회", description = "홈 화면에서 공고 5개를 최신 순으로 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "홈 화면 공고 5개 조회 성공")})
    @GetMapping(value = "/volunteers/posts/home")
    public ResponseEntity<List<PostGetHomeResponse>> getHomePosts() {
        List<PostGetHomeResponse> homePosts = postService.getHomePosts();
        return ResponseEntity.ok(homePosts);
    }

    @Operation(summary = "공고 필터 검색", description = "공고를 필터로 검색합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "공고 필터 검색 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "P1, 잘못된 공고 상태입니다. \t\n D1, 잘못된 강아지 사이즈입니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/volunteers/posts")
    public ResponseEntity<List<PostSearchResponse>> searchPosts(PostSearchRequest request, Pageable pageable) {
        List<PostSearchResponse> response = postService.searchPosts(request, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "공고 상세 보기", description = "공고 상세 정보를 조회합니다.",
            responses = {@ApiResponse(responseCode = "200", description = "공고 상세 정보 조회 성공")
                    , @ApiResponse(responseCode = "400"
                    , description = "M1, 해당 이동봉사자를 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping( "/volunteers/posts/{postId}")
    public ResponseEntity<PostGetOneResponse> getOnePost(@AuthenticationPrincipal UserDetails loginUser, @PathVariable Long postId) {
        PostGetOneResponse onePost = postService.getOnePost(loginUser.getUsername(), postId);
        return ResponseEntity.ok(onePost);
    }
}
