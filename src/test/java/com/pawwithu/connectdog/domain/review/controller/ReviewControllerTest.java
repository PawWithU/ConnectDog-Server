package com.pawwithu.connectdog.domain.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pawwithu.connectdog.domain.review.dto.request.ReviewCreateRequest;
import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetAllResponse;
import com.pawwithu.connectdog.domain.review.dto.response.ReviewGetOneResponse;
import com.pawwithu.connectdog.domain.review.service.ReviewService;
import com.pawwithu.connectdog.utils.TestUserArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;
    @Mock
    private ReviewService reviewService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver(), new PageableHandlerMethodArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 후기_등록() throws Exception {
        // given
        Long postId = 1L;
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest("이동봉사 리뷰 테스트 - 봄이는 귀엽고 예쁘고 차분하다!");

        MockMultipartFile files = new MockMultipartFile("files", "image1.png", "multipart/form-data", "uploadFile".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile request = new MockMultipartFile("request", "", "application/json", objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(reviewCreateRequest).getBytes(StandardCharsets.UTF_8));

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/volunteers/posts/{postId}/reviews", postId)
                .file(request)
                .file(files)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        // then
        result.andExpect(status().isNoContent());
        verify(reviewService, times(1)).createReview(anyString(), anyLong(), any(), any());
    }

    @Test
    void 후기_단건_조회() throws Exception {
        // given
        Long reviewId = 1L;
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");

        ReviewGetOneResponse response = new ReviewGetOneResponse("겨울이", "호짱", "mainImage", images, startDate, endDate,
                "서울시 노원구", "서울시 성북구", "이동봉사 중개", "후기 조회 테스트입니다.");

        // when
        given(reviewService.getOneReview(anyString(), anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/reviews/{reviewId}", reviewId)
        );

        // then
        result.andExpect(status().isOk());
        verify(reviewService, times(1)).getOneReview(anyString(), anyLong());
    }

    @Test
    void 후기_전체_조회() throws Exception {
        // given
        List<ReviewGetAllResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);

        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");

        response.add(new ReviewGetAllResponse("봄이", "호짱", "mainImage", images, startDate, endDate,
                "서울시 노원구", "서울시 성북구", "이동봉사 중개", "후기 조회 테스트입니다."));
        response.add(new ReviewGetAllResponse("겨울이", "호짱", "mainImage", images, startDate, endDate,
                "서울시 노원구", "서울시 성북구", "이동봉사 중개", "후기 조회 테스트입니다."));

        // when
        given(reviewService.getAllReviews(any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/reviews")
                        .param("page", "0")
                        .param("size", "2")
        );

        // then
        result.andExpect(status().isOk());
        verify(reviewService, times(1)).getAllReviews(any());
    }
}