package com.pawwithu.connectdog.domain.intermediary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetDogStatusesResponse;
import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetInfoResponse;
import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetPostsResponse;
import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetReviewsResponse;
import com.pawwithu.connectdog.domain.intermediary.service.IntermediaryService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class IntermediaryControllerTest {

    @InjectMocks
    private IntermediaryController intermediaryController;
    @Mock
    private IntermediaryService intermediaryService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(intermediaryController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver(), new PageableHandlerMethodArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 이동봉사_중개_모집중_공고_목록_조회() throws Exception {
        //given
        Long intermediaryId = 1L;
        Pageable pageable = PageRequest.of(0, 2);
        List<IntermediaryGetPostsResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new IntermediaryGetPostsResponse(1L, "image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "중개자하노정", false));
        response.add(new IntermediaryGetPostsResponse(2L, "image2", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "중개자하노정", true));


        //when
        given(intermediaryService.getIntermediaryPosts(anyLong(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/intermediaries/{intermediaryId}/posts", intermediaryId)
        );

        //then
        result.andExpect(status().isOk());
        verify(intermediaryService, times(1)).getIntermediaryPosts(anyLong(), any());
    }

    @Test
    void 이동봉사_중개_프로필_기본_정보_조회() throws Exception {
        // given
        Long intermediaryId = 1L;
        IntermediaryGetInfoResponse response = new IntermediaryGetInfoResponse("profileImage", 3L,
                "이동봉사 중개 이름", "안녕하세요. 한 줄 소개 입니다.", "https://connectdog.site", "인스타그램: @hoxjeong", "안내 사항입니다.", 25L, 20L);

        // when
        given(intermediaryService.getIntermediaryInfo(anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/intermediaries/{intermediaryId}", intermediaryId)
        );

        // then
        result.andExpect(status().isOk());
        verify(intermediaryService, times(1)).getIntermediaryInfo(anyLong());
    }

    @Test
    void 이동봉사_중개_프로필_후기_조회() throws Exception {
        // given
        Long intermediaryId = 1L;
        List<IntermediaryGetReviewsResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);

        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");

        response.add(new IntermediaryGetReviewsResponse("봄이", "호짱", "mainImage", images, startDate, endDate,
                "서울시 노원구", "서울시 성북구", "이동봉사 중개", "후기 조회 테스트입니다."));
        response.add(new IntermediaryGetReviewsResponse("겨울이", "호짱", "mainImage", images, startDate, endDate,
                "서울시 노원구", "서울시 성북구", "이동봉사 중개", "후기 조회 테스트입니다."));

        // when
        given(intermediaryService.getIntermediaryReviews(anyLong(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/intermediaries/{intermediaryId}/reviews", intermediaryId)
                        .param("page", "0")
                        .param("size", "2")
        );

        // then
        result.andExpect(status().isOk());
        verify(intermediaryService, times(1)).getIntermediaryReviews(anyLong(), any());
    }

    @Test
    void 이동봉사_중개_프로필_근황_조회() throws Exception {
        // given
        Long intermediaryId = 1L;
        List<IntermediaryGetDogStatusesResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);

        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");

        response.add(new IntermediaryGetDogStatusesResponse("겨울이", "호짱", "mainImage", images, startDate, endDate,
                "서울시 노원구", "서울시 성북구", "근황 조회 테스트입니다."));
        response.add(new IntermediaryGetDogStatusesResponse("봄이", "경혁쨩", "mainImage", images, startDate, endDate,
                "서울시 노원구", "서울시 성북구", "근황 조회 테스트입니다."));

        // when
        given(intermediaryService.getIntermediaryDogStatuses(anyLong(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/intermediaries/{intermediaryId}/dogStatuses", intermediaryId)
                        .param("page", "0")
                        .param("size", "2")
        );

        // then
        result.andExpect(status().isOk());
        verify(intermediaryService, times(1)).getIntermediaryDogStatuses(anyLong(), any());
    }


}