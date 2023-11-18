package com.pawwithu.connectdog.domain.dogStatus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pawwithu.connectdog.domain.dogStatus.dto.request.DogStatusCreateRequest;
import com.pawwithu.connectdog.domain.dogStatus.dto.response.DogStatusIntermediaryGetOneResponse;
import com.pawwithu.connectdog.domain.dogStatus.dto.response.DogStatusVolunteerGetOneResponse;
import com.pawwithu.connectdog.domain.dogStatus.service.DogStatusService;
import com.pawwithu.connectdog.utils.TestUserArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
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

class DogStatusControllerTest {

    @InjectMocks
    private DogStatusController dogStatusController;
    @Mock
    private DogStatusService dogStatusService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dogStatusController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver(), new PageableHandlerMethodArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 근황_등록() throws Exception {
        // given
        Long postId = 1L;
        DogStatusCreateRequest dogStatusCreateRequest = new DogStatusCreateRequest("강아지 근황 - 겨울이는 호짱님 덕분에 잘 지내고 있답니다 :)");

        MockMultipartFile files = new MockMultipartFile("files", "image1.png", "multipart/form-data", "uploadFile".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile request = new MockMultipartFile("request", "", "application/json", objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(dogStatusCreateRequest).getBytes(StandardCharsets.UTF_8));

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .multipart(HttpMethod.POST, "/intermediaries/posts/{postId}/dogStatus", postId)
                .file(request)
                .file(files)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        // then
        result.andExpect(status().isNoContent());
        verify(dogStatusService, times(1)).createDogStatus(anyString(), anyLong(), any(), any());
    }

    @Test
    void 이동봉사_중개_근황_단건_조회() throws Exception {
        // given
        Long dogStatusId = 1L;
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");

        DogStatusIntermediaryGetOneResponse response = new DogStatusIntermediaryGetOneResponse("겨울이", "호짱", "mainImage", images, startDate, endDate,
                "서울시 노원구", "서울시 성북구", "근황 조회 테스트입니다.");

        // when
        given(dogStatusService.getIntermediaryOneDogStatus(anyString(), anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/intermediaries/dogStatus/{dogStatusId}", dogStatusId)
        );

        // then
        result.andExpect(status().isOk());
        verify(dogStatusService, times(1)).getIntermediaryOneDogStatus(anyString(), anyLong());
    }

    @Test
    void 이동봉사자_근황_단건_조회() throws Exception {
        // given
        Long dogStatusId = 1L;
        LocalDate startDate = LocalDate.of(2023, 10, 2);
        LocalDate endDate = LocalDate.of(2023, 11, 7);
        List<String> images = new ArrayList<>();
        images.add("image1");
        images.add("image2");

        DogStatusVolunteerGetOneResponse response = new DogStatusVolunteerGetOneResponse("겨울이", "호짱", "mainImage", images, startDate, endDate,
                "서울시 노원구", "서울시 성북구", "근황 조회 테스트입니다.");

        // when
        given(dogStatusService.getVolunteerOneDogStatus(anyString(), anyLong())).willReturn(response);
        ResultActions result = mockMvc.perform(
                get("/volunteers/dogStatus/{dogStatusId}", dogStatusId)
        );

        // then
        result.andExpect(status().isOk());
        verify(dogStatusService, times(1)).getVolunteerOneDogStatus(anyString(), anyLong());
    }
}