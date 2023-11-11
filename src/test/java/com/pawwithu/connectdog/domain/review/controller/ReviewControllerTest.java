package com.pawwithu.connectdog.domain.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pawwithu.connectdog.domain.review.dto.request.ReviewCreateRequest;
import com.pawwithu.connectdog.domain.review.service.ReviewService;
import com.pawwithu.connectdog.utils.TestUserArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
                .setCustomArgumentResolvers(new TestUserArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 후기_등록() throws Exception {
        // given
        Long postId = 1L;
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest("이동봉사 리뷰입니다.");

        MockMultipartFile files = new MockMultipartFile("files", "image1.png", "multipart/form-data", "uploadFile".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile request = new MockMultipartFile("request", "", "application/json", objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(reviewCreateRequest).getBytes(StandardCharsets.UTF_8));

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/volunteers/reviews/{postId}", postId)
                .file(request)
                .file(files)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        // then
        result.andExpect(status().isNoContent());
        verify(reviewService, times(1)).createReview(anyString(), anyLong(), any(), any());
    }
}