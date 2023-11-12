package com.pawwithu.connectdog.domain.volunteer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.volunteer.dto.request.AdditionalAuthRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.request.NicknameRequest;
import com.pawwithu.connectdog.domain.volunteer.dto.response.NicknameResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}