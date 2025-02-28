package com.pawwithu.connectdog.domain.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationIntermediaryGetOneResponse;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationVolunteerGetOneResponse;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationsIntermediaryGetResponse;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationsVolunteerGetResponse;
import com.pawwithu.connectdog.domain.notification.entity.NotificationType;
import com.pawwithu.connectdog.domain.notification.service.NotificationService;
import com.pawwithu.connectdog.utils.TestUserArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;
    @Mock
    private NotificationService notificationService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver(), new PageableHandlerMethodArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 봉사자_알림_목록_조회() throws Exception {
        //given
        List<NotificationsVolunteerGetResponse> response = new ArrayList<>();
        response.add(new NotificationsVolunteerGetResponse(1L, "image1", NotificationType.CONFIRMED, "제목", "내용",
                false, 1L));
        response.add(new NotificationsVolunteerGetResponse(2L, "image2", NotificationType.REJECTED, "제목", "내용",
                false, 2L));

        //when
        given(notificationService.getVolunteerNotifications(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/notifications/my")
        );

        //then
        result.andExpect(status().isOk());
        verify(notificationService, times(1)).getVolunteerNotifications(anyString(), any());
    }

    @Test
    void 모집자_알림_목록_조회() throws Exception {
        //given
        List<NotificationsIntermediaryGetResponse> response = new ArrayList<>();
        response.add(new NotificationsIntermediaryGetResponse(1L, NotificationType.CONFIRMED, "제목", "내용",
                false, 1L, "2024-07-28"));
        response.add(new NotificationsIntermediaryGetResponse(2L, NotificationType.REJECTED, "제목", "내용",
                false, 2L, "2024-07-28"));

        //when
        given(notificationService.getIntermediaryNotifications(anyString(), any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/intermediaries/notifications/my")
        );

        //then
        result.andExpect(status().isOk());
        verify(notificationService, times(1)).getIntermediaryNotifications(anyString(), any());
    }

    @Test
    void 모집자_알림_단건_조회() throws Exception {
        //given
        Long notificationId = 1L;
        NotificationIntermediaryGetOneResponse response = new NotificationIntermediaryGetOneResponse(1L, NotificationType.COMPLETED.getKey(), "title", "body", false, "2024-07-28");

        //when
        given(notificationService.getIntermediaryOneNotification(anyString(), anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/intermediaries/notifications/{notificationId}", notificationId)
        );

        //then
        result.andExpect(status().isOk());
        verify(notificationService, times(1)).getIntermediaryOneNotification(anyString(), anyLong());
    }

    @Test
    void 봉사자_알림_단건_조회() throws Exception {
        //given
        Long notificationId = 1L;
        NotificationVolunteerGetOneResponse response = new NotificationVolunteerGetOneResponse(1L, "mainImage", NotificationType.COMPLETED.getKey(), "T1", "B1", false);

        //when
        given(notificationService.getVolunteerOneNotification(anyString(), anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/notifications/{notificationId}", notificationId)
        );

        //then
        result.andExpect(status().isOk());
        verify(notificationService, times(1)).getVolunteerOneNotification(anyString(), anyLong());
    }
}