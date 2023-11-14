package com.pawwithu.connectdog.domain.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.application.dto.request.VolunteerApplyRequest;
import com.pawwithu.connectdog.domain.application.dto.response.ApplicationGetOneResponse;
import com.pawwithu.connectdog.domain.application.dto.response.ApplicationProgressingResponse;
import com.pawwithu.connectdog.domain.application.dto.response.ApplicationWaitingResponse;
import com.pawwithu.connectdog.domain.application.service.ApplicationService;
import com.pawwithu.connectdog.utils.TestUserArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {

    @InjectMocks
    private ApplicationController applicationController;
    @Mock
    private ApplicationService applicationService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver(), new PageableHandlerMethodArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 이동봉사_신청() throws Exception{
        //given
        Long postId = 1L;
        VolunteerApplyRequest request = new VolunteerApplyRequest("하노정",
                "01022223333",
                "자동차",
                "이동봉사 신청하겠습니다!");

        //when
        ResultActions result = mockMvc.perform(
                post("/volunteers/posts/{postId}/applications", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        result.andExpect(status().isNoContent());
        verify(applicationService, times(1)).volunteerApply(anyString(), anyLong(), any());
    }

    @Test
    void 이동봉사_승인_대기중_목록_조회() throws Exception {
        //given
        List<ApplicationWaitingResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new ApplicationWaitingResponse("image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", true, 1L));
        response.add(new ApplicationWaitingResponse("image2", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", false, 2L));

        //when
        given(applicationService.getWaitingApplications(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/applications/waiting")
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getWaitingApplications(anyString(), any());
    }

    @Test
    void 이동봉사_진행중_목록_조회() throws Exception {
        //given
        List<ApplicationProgressingResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new ApplicationProgressingResponse("image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", true));
        response.add(new ApplicationProgressingResponse("image2", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", false));

        //when
        given(applicationService.getProgressingApplications(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/applications/progressing")
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getProgressingApplications(anyString(), any());
    }

    @Test
    void 이동봉사_신청내역_단건_조회() throws Exception {
        //given
        ApplicationGetOneResponse response = new ApplicationGetOneResponse("한호정", "01022223333", "자동차", "이동봉사 신청합니다.");
        Long applicationId = 1L;

        //when
        given(applicationService.getOneApplication(anyString(), anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/applications/{applicationId}", applicationId)
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getOneApplication(anyString(), anyLong());
    }
}