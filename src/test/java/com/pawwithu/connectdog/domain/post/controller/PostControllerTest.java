package com.pawwithu.connectdog.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pawwithu.connectdog.domain.dog.entity.DogGender;
import com.pawwithu.connectdog.domain.dog.entity.DogSize;
import com.pawwithu.connectdog.domain.post.dto.PostCreateRequest;
import com.pawwithu.connectdog.domain.post.service.PostService;
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
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @InjectMocks
    private PostController postController;
    @Mock
    private PostService postService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

//    @Test
//    void 이동봉사_중개_공고_등록() throws Exception {
//        //given
//        LocalDate startDate = LocalDate.of(2023, 10, 2);
//        LocalDate endDate = LocalDate.of(2023, 11, 7);
//        PostCreateRequest postCreateRequest = new PostCreateRequest("서울시 성북구",
//                "서울시 중랑구",
//                startDate,
//                endDate,
//                "12:00",
//                true,
//                "공고 내용",
//                "봄이",
//                DogSize.SMALL,
//                DogGender.FEMALE,
//                2.1f,
//                "특이사항");
//
//        MockMultipartFile files = new MockMultipartFile("files", "image1.png", "multipart/form-data", "uploadFile".getBytes(StandardCharsets.UTF_8));
//        MockMultipartFile request = new MockMultipartFile("request", null, "application/json", objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(postCreateRequest).getBytes(StandardCharsets.UTF_8));
//
//        //when
//        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .multipart(HttpMethod.POST, "/intermediaries/posts")
//                .file(request)
//                .file(files)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.MULTIPART_FORM_DATA));
//
//        //then
//        result.andExpect(status().isNoContent());
//        verify(postService, times(1)).createPost(any(), any(), any());
//    }

}