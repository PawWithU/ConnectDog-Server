package com.pawwithu.connectdog.domain.intermediary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetPostsResponse;
import com.pawwithu.connectdog.domain.intermediary.service.IntermediaryService;
import com.pawwithu.connectdog.domain.post.dto.response.PostRecruitingGetResponse;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
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

}