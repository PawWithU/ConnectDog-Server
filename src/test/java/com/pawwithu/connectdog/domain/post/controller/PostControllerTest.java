package com.pawwithu.connectdog.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetHomeResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostSearchResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        response.add(new PostGetHomeResponse("image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", true));
        response.add(new PostGetHomeResponse("image2", "서울시 성북구", "서울시 중랑구",
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
        response.add(new PostSearchResponse("image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", true));
        response.add(new PostSearchResponse("image2", "서울시 성북구", "서울시 중랑구",
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
                        .param("page", "0")
                        .param("size", "2")
        );

        //then
        result.andExpect(status().isOk());
        verify(postService, times(1)).searchPosts(any(), any());
    }

}