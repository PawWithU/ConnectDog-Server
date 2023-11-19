package com.pawwithu.connectdog.domain.volunteer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetHomeResponse;
import com.pawwithu.connectdog.domain.volunteer.dto.request.AdditionalAuthRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.NicknameRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.VolunteerMyProfileRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.response.NicknameResponse;
import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBadgeResponse;
import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBookmarkResponse;
import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyInfoResponse;
import com.pawwithu.connectdog.domain.volunteer.service.VolunteerService;
import com.pawwithu.connectdog.utils.TestUserArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class VolunteerControllerTest {

    @InjectMocks
    private VolunteerController volunteerController;
    @Mock
    private VolunteerService volunteerService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(volunteerController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 닉네임_중복_검사() throws Exception {
        //given
        NicknameRequest request = new NicknameRequest("코넥독123");
        NicknameResponse response = new NicknameResponse(false);

        //when
        given(volunteerService.isNicknameDuplicated(request)).willReturn(response);
        ResultActions result = mockMvc.perform(
                post("/volunteers/nickname/isDuplicated")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        result.andExpect(status().isOk());
        verify(volunteerService, times(1)).isNicknameDuplicated(request);
    }

    @Test
    void 이동봉사자_추가_인증() throws Exception {
        //given
        AdditionalAuthRequest request = new AdditionalAuthRequest("하노정", "01010101010");

        //when
        ResultActions result = mockMvc.perform(
                post("/volunteers/additional-auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        result.andExpect(status().isNoContent());
        verify(volunteerService, times(1)).additionalAuth(anyString(), any());
    }

    @Test
    void 이동봉사자_마이페이지_통계_정보_조회() throws Exception {
        // given
        VolunteerGetMyInfoResponse response = VolunteerGetMyInfoResponse.of(1L, 3L, 5L);

        // when
        given(volunteerService.getMyInfo(any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/my/info")
        );

        // then
        result.andExpect(status().isOk());
        verify(volunteerService, times(1)).getMyInfo(any());
    }

    @Test
    void 이동봉사자_마이페이지_북마크한_공고_목록_조회() throws Exception {
        // given
        List<VolunteerGetMyBookmarkResponse> response = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        response.add(new VolunteerGetMyBookmarkResponse(1L, "image1", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", true));
        response.add(new VolunteerGetMyBookmarkResponse(2L, "image2", "서울시 성북구", "서울시 중랑구",
                startDate, endDate, "이동봉사 중개", false));

        // when
        given(volunteerService.getMyBookmarks(any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/my/bookmarks")
        );

        // then
        result.andExpect(status().isOk());
        verify(volunteerService, times(1)).getMyBookmarks(any());
    }

    @Test
    void 이동봉사자_마이페이지_활동_배지_목록_조회() throws Exception {
        // given
        List<VolunteerGetMyBadgeResponse> response = new ArrayList<>();
        response.add(new VolunteerGetMyBadgeResponse(1L, "코넥독 후기왕1", "image1"));
        response.add(new VolunteerGetMyBadgeResponse(2L, "코넥독 후기왕2", "image2"));
        response.add(new VolunteerGetMyBadgeResponse(2L, "코넥독 후기왕3", null));

        // when
        given(volunteerService.getMyBadges(any())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/my/badges")
        );

        // then
        result.andExpect(status().isOk());
        verify(volunteerService, times(1)).getMyBadges(any());
    }

    @Test
    void 이동봉사자_마이페이지_프로필_수정() throws Exception {
        // given
        VolunteerMyProfileRequest request = new VolunteerMyProfileRequest("하노짱", 2);

        // when
        ResultActions result = mockMvc.perform(
                patch("/volunteers/my/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // then
        result.andExpect(status().isNoContent());
        verify(volunteerService, times(1)).volunteerMyProfile(anyString(), any());

    }
}