package com.pawwithu.connectdog.domain.fcm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.application.dto.request.VolunteerApplyRequest;
import com.pawwithu.connectdog.domain.fcm.dto.request.IntermediaryFcmRequest;
import com.pawwithu.connectdog.domain.fcm.dto.request.VolunteerFcmRequest;
import com.pawwithu.connectdog.domain.fcm.service.FcmService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FcmControllerTest {

    @InjectMocks
    private FcmController fcmController;
    @Mock
    private FcmService fcmService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fcmController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver(), new PageableHandlerMethodArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 이동봉사자_FCM_토큰_저장() throws Exception{
        //given
        VolunteerFcmRequest request = new VolunteerFcmRequest("fcmToken");

        //when
        ResultActions result = mockMvc.perform(
                post("/volunteers/fcm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        result.andExpect(status().isNoContent());
        verify(fcmService, times(1)).saveVolunteerFcm(anyString(), any());
    }

    @Test
    void 이동봉사_중개_FCM_토큰_저장() throws Exception{
        //given
        IntermediaryFcmRequest request = new IntermediaryFcmRequest("fcmToken");

        //when
        ResultActions result = mockMvc.perform(
                post("/intermediaries/fcm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        result.andExpect(status().isNoContent());
        verify(fcmService, times(1)).saveIntermediaryFcm(anyString(), any());
    }
}