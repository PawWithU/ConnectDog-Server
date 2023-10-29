package com.pawwithu.connectdog.domain.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.auth.dto.request.EmailRequest;
import com.pawwithu.connectdog.domain.auth.dto.request.IntermediarySignUpRequest;
import com.pawwithu.connectdog.domain.auth.dto.request.VolunteerSignUpRequest;
import com.pawwithu.connectdog.domain.auth.dto.response.EmailResponse;
import com.pawwithu.connectdog.domain.auth.service.AuthService;
import com.pawwithu.connectdog.domain.auth.service.EmailService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SignUpControllerTest {

    @InjectMocks
    private SignUpController signUpController;
    @Mock
    private AuthService authService;
    @Mock
    private EmailService emailService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(signUpController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }
    @Test
    void 이메일_인증번호_전송() throws Exception{
        //given
        EmailRequest request = new EmailRequest("email@naver.com");
        EmailResponse response = new EmailResponse("authCode123");

        //when
        when(emailService.sendEmail(any())).thenReturn(response);
        ResultActions result = mockMvc.perform(
                post("/volunteers/sign-up/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        result.andExpect(status().isOk());
        verify(emailService, times(1)).sendEmail(any());
    }


    @Test
    void 이동봉사자_자체_회원가입() throws Exception{
        //given
        VolunteerSignUpRequest request = new VolunteerSignUpRequest("email@naver.com",
                "pasword12345",
                "코넥독",
                false);
        //when
        ResultActions result = mockMvc.perform(
                post("/volunteers/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );
        //then
        result.andExpect(status().isNoContent());
        verify(authService, times(1)).volunteerSignUp(any());
    }

    @Test
    void 이동봉사_중개_자체_회원가입() throws Exception{
        //given
        IntermediarySignUpRequest request = new IntermediarySignUpRequest("email@naver.com",
                "pasword12345",
                "이동봉사 단체",
                "https://connectdog.site",
                false);

        MockMultipartFile file = new MockMultipartFile("file", "authImage.png", "multipart/form-data", "uploadFile".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile intermediarySignUpRequest = new MockMultipartFile("request", null, "application/json", objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8));

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/intermediaries/sign-up")
                        .file(file)
                        .file(intermediarySignUpRequest)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.MULTIPART_FORM_DATA));

        //then
        result.andExpect(status().isNoContent());
        verify(authService, times(1)).intermediarySignUp(any(), any());
    }
}