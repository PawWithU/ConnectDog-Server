package com.pawwithu.connectdog.domain.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.application.dto.request.VolunteerApplyRequest;
import com.pawwithu.connectdog.domain.application.dto.response.*;
import com.pawwithu.connectdog.domain.application.service.ApplicationService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        List<ApplicationVolunteerWaitingResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new ApplicationVolunteerWaitingResponse(1L, "image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", true, 1L));
        response.add(new ApplicationVolunteerWaitingResponse(2L, "image2", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", false, 2L));

        //when
        given(applicationService.getVolunteerWaitingApplications(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/applications/waiting")
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getVolunteerWaitingApplications(anyString(), any());
    }

    @Test
    void 이동봉사_진행중_목록_조회() throws Exception {
        //given
        List<ApplicationVolunteerProgressingResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new ApplicationVolunteerProgressingResponse(1L, "image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", true));
        response.add(new ApplicationVolunteerProgressingResponse(2L, "image2", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", false));

        //when
        given(applicationService.getVolunteerProgressingApplications(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/applications/progressing")
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getVolunteerProgressingApplications(anyString(), any());
    }

    @Test
    void 이동봉사자_신청내역_단건_조회() throws Exception {
        //given
        ApplicationVolunteerGetOneResponse response = new ApplicationVolunteerGetOneResponse(1L, "한호정", "01022223333", "자동차", "이동봉사 신청합니다.");
        Long applicationId = 1L;

        //when
        given(applicationService.getVolunteerOneApplication(anyString(), anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/applications/{applicationId}", applicationId)
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getVolunteerOneApplication(anyString(), anyLong());
    }

    @Test
    void 이동봉사_신청_취소() throws Exception {
        //given
        Long applicationId = 1L;

        //when
        ResultActions result = mockMvc.perform(
                delete("/volunteers/applications/{applicationId}", applicationId)
        );

        //then
        result.andExpect(status().isNoContent());
        verify(applicationService, times(1)).deleteApplication(anyString(), anyLong());
    }

    @Test
    void 이동봉사_신청_승인() throws Exception {
        //given
        Long applicationId = 1L;

        //when
        ResultActions result = mockMvc.perform(
                patch("/intermediaries/applications/{applicationId}", applicationId)
        );

        //then
        result.andExpect(status().isNoContent());
        verify(applicationService, times(1)).confirmApplication(anyString(), anyLong());
    }

    @Test
    void 이동봉사_신청_반려() throws Exception {
        //given
        Long applicationId = 1L;

        //when
        ResultActions result = mockMvc.perform(
                delete("/intermediaries/applications/{applicationId}", applicationId)
        );

        //then
        result.andExpect(status().isNoContent());
        verify(applicationService, times(1)).cancelApplication(anyString(), anyLong());
    }

    @Test
    void 이동봉사_중개_승인_대기중_공고_목록_조회() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 2);
        List<ApplicationIntermediaryWaitingResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new ApplicationIntermediaryWaitingResponse(1L, "image1", "포포1", startDate, endDate,
                "서울시 성북구", "서울시 중랑구", "하노정", 1L));
        response.add(new ApplicationIntermediaryWaitingResponse(2L, "image2", "포포2", startDate, endDate,
                "서울시 성북구", "서울시 중랑구", "민경혁", 2L));


        //when
        given(applicationService.getIntermediaryWaitingApplications(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/intermediaries/applications/waiting")
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getIntermediaryWaitingApplications(anyString(), any());
    }

    @Test
    void 이동봉사_중개_진행중_공고_목록_조회() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 2);
        List<ApplicationIntermediaryProgressingResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new ApplicationIntermediaryProgressingResponse(1L, "image1", "포포1", startDate, endDate,
                "서울시 성북구", "서울시 중랑구", "하노정", 1L));
        response.add(new ApplicationIntermediaryProgressingResponse(2L, "image2", "포포2", startDate, endDate,
                "서울시 성북구", "서울시 중랑구", "민경혁", 2L));


        //when
        given(applicationService.getIntermediaryProgressingApplications(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/intermediaries/applications/progressing")
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getIntermediaryProgressingApplications(anyString(), any());
    }

    @Test
    void 이동봉사자_봉사완료_목록_조회() throws Exception {
        //given
        List<ApplicationVolunteerCompletedResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new ApplicationVolunteerCompletedResponse(1L, "image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", true, 1L, null));
        response.add(new ApplicationVolunteerCompletedResponse(2L, "image2", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", false, null, null));

        //when
        given(applicationService.getVolunteerCompletedApplications(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/applications/completed")
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getVolunteerCompletedApplications(anyString(), any());
    }

    @Test
    void 이동봉사_중개_봉사완료_목록_조회() throws Exception {
        //given
        List<ApplicationIntermediaryCompletedResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new ApplicationIntermediaryCompletedResponse(1L, "image1", "포포",
                startDate, endDate, "이동봉사 중개", "서울시 성북구", "서울시 중랑구",
                1L, 1L, null));
        response.add(new ApplicationIntermediaryCompletedResponse(2L, "image1", "포포",
                startDate, endDate, "이동봉사 중개", "서울시 성북구", "서울시 중랑구",
                1L, 1L, null));

        //when
        given(applicationService.getIntermediaryCompletedApplications(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/intermediaries/applications/completed")
                        .param("page", "0")
                        .param("size", "2")
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getIntermediaryCompletedApplications(anyString(), any());
    }

    @Test
    void 이동봉사_중개_신청내역_단건_조회() throws Exception {
        //given
        ApplicationIntermediaryGetOneResponse response = new ApplicationIntermediaryGetOneResponse(1L, "한호정", "01022223333", "자동차", "이동봉사 신청합니다.");
        Long applicationId = 1L;

        //when
        given(applicationService.getIntermediaryOneApplication(anyString(), anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/intermediaries/applications/{applicationId}", applicationId)
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getIntermediaryOneApplication(anyString(), anyLong());
    }

    @Test
    void 이동봉사_신청_기본_정보_불러오기() throws Exception {
        //given
        ApplicationVolunteerInfoResponse response = new ApplicationVolunteerInfoResponse("한호정", "01022223333");

        //when
        ResultActions result = mockMvc.perform(
                get("/volunteers/applications/my-info")
        );

        //then
        result.andExpect(status().isOk());
        verify(applicationService, times(1)).getMyInfo(anyString());
    }

    @Test
    void 이동봉사_완료() throws Exception {
        //given
        Long applicationId = 1L;

        //when
        ResultActions result = mockMvc.perform(
                patch("/intermediaries/applications/{applicationId}/completed", applicationId)
        );

        //then
        result.andExpect(status().isNoContent());
        verify(applicationService, times(1)).completeApplication(anyString(), anyLong());
    }

}