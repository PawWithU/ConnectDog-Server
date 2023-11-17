package com.pawwithu.connectdog.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.dog.entity.DogGender;
import com.pawwithu.connectdog.domain.dog.entity.DogSize;
import com.pawwithu.connectdog.domain.post.dto.response.*;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import com.pawwithu.connectdog.domain.post.service.PostService;
import com.pawwithu.connectdog.utils.TestUserArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @InjectMocks
    private PostController postController;
    @Mock
    private PostService postService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver(), new PageableHandlerMethodArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

//    @Test
//    void 이동봉사_중개_공고_등록() throws Exception {
//        //given
//        LocalDate startDate = LocalDate.of(2023, 10, 2);
//        LocalDate endDate = LocalDate.of(2023, 11, 7);
//        PostCreateRequest postCreateRequest = new PostCreateRequest("서울시 성북구",
//                "서울시 중랑구",
//                startDate,
//                endDate,
//                "12:00",
//                true,
//                "공고 내용",
//                "봄이",
//                DogSize.SMALL,
//                DogGender.FEMALE,
//                2.1f,
//                "특이사항");
//
//        MockMultipartFile files = new MockMultipartFile("files", "image1.png", "multipart/form-data", "uploadFile".getBytes(StandardCharsets.UTF_8));
//        MockMultipartFile request = new MockMultipartFile("request", null, "application/json", objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(postCreateRequest).getBytes(StandardCharsets.UTF_8));
//
//        //when
//        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .multipart(HttpMethod.POST, "/intermediaries/posts")
//                .file(request)
//                .file(files)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.MULTIPART_FORM_DATA));
//
//        //then
//        result.andExpect(status().isNoContent());
//        verify(postService, times(1)).createPost(any(), any(), any());
//    }

    @Test
    void 홈_공고_5개_조회() throws Exception {
        //given
        List<PostGetHomeResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new PostGetHomeResponse(1L, "image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", true));
        response.add(new PostGetHomeResponse(2L, "image2", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", false));

        //when
        given(postService.getHomePosts()).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/posts/home")
        );

        //then
        result.andExpect(status().isOk());
        verify(postService, times(1)).getHomePosts();
    }

    @Test
    void 공고_필터별_검색() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 2);
        List<PostSearchResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new PostSearchResponse(1L, "image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", true));
        response.add(new PostSearchResponse(2L, "image2", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", false));


        //when
        given(postService.searchPosts(any(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/posts")
//                        .param("postStatus", "모집중")
                        .param("departureLoc", "서울시 성북구")
                        .param("arrivalLoc", "경기 고양시")
                        .param("startDate", "2023-10-02")
                        .param("endDate", "2023-11-07")
//                        .param("dogSize", DogSize.SMALL.getKey())
                        .param("isKennel", "false")
                        .param("intermediaryName", "이동봉사 중개")
                        .param("orderCondition", "마감순")
                        .param("page", "0")
                        .param("size", "2")
        );

        //then
        result.andExpect(status().isOk());
        verify(postService, times(1)).searchPosts(any(), any());
    }

    @Test
    void 이동봉사자_공고_상세_보기() throws Exception {
        //given
        Long postId = 1L;
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");
        PostIntermediaryGetOneResponse response = new PostIntermediaryGetOneResponse(1L, "mainImage", images, "모집중", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "12:00", true, "이동봉사 공고", "봄이", DogSize.SMALL.getKey(),
                DogGender.FEMALE.getKey(), 5.1f, "ㄱㅇㅇ", 1L, "profileImage", "이동봉사 중개");


        //when
        given(postService.getIntermediaryOnePost(anyString(), anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/intermediaries/posts/{postId}", postId)
        );

        //then
        result.andExpect(status().isOk());
        verify(postService, times(1)).getIntermediaryOnePost(anyString(), anyLong());
    }

    @Test
    void 이동봉사_중개_모집중_공고_목록_조회() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 2);
        List<PostRecruitingGetResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new PostRecruitingGetResponse(1L, PostStatus.RECRUITING, "image1", "포포1", startDate, endDate,
                "서울시 성북구", "서울시 중랑구"));
        response.add(new PostRecruitingGetResponse(2L, PostStatus.EXPIRED, "image2", "포포2", startDate, endDate,
                "서울시 성북구", "서울시 중랑구"));


        //when
        given(postService.getRecruitingPosts(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/intermediaries/posts/recruiting")
        );

        //then
        result.andExpect(status().isOk());
        verify(postService, times(1)).getRecruitingPosts(anyString(), any());
    }

    @Test
    void 이동봉사_중개_공고_삭제() throws Exception {
        //given
        Long postId = 1L;

        //when
        ResultActions result = mockMvc.perform(
                delete("/intermediaries/posts/{postId}", postId)
        );

        //then
        result.andExpect(status().isNoContent());
        verify(postService, times(1)).deletePost(anyString(), anyLong());
    }

    @Test
    void 이동봉사_중개_공고_상세_보기() throws Exception {
        //given
        Long postId = 1L;
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");
        PostVolunteerGetOneResponse response = new PostVolunteerGetOneResponse(1L, "mainImage", images, "모집중", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "12:00", true, "이동봉사 공고", "봄이", DogSize.SMALL.getKey(),
                DogGender.FEMALE.getKey(), 5.1f, "ㄱㅇㅇ", 1L, "profileImage", "이동봉사 중개", true);


        //when
        given(postService.getVolunteerOnePost(anyString(), anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/posts/{postId}", postId)
        );
    }
}