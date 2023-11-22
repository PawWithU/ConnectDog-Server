package com.pawwithu.connectdog.domain.bookmark.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.bookmark.service.BookmarkService;
import com.pawwithu.connectdog.utils.TestUserArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookmarkControllerTest {

    @InjectMocks
    private BookmarkController bookmarkController;
    @Mock
    private BookmarkService bookmarkService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookmarkController)
                .setCustomArgumentResolvers(new TestUserArgumentResolver())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void 이동봉사자_북마크_등록() throws Exception {
        // given
        Long postId = 1L;

        // when
        ResultActions result = mockMvc.perform(
                post("/volunteers/posts/{postId}/bookmarks", postId)
        );

        // then
        result.andExpect(status().isNoContent());
        verify(bookmarkService, times(1)).createBookmark(anyString(), anyLong());
    }

    @Test
    void 이동봉사자_북마크_삭제() throws Exception {
        // given
        Long postId = 1L;

        // when
        ResultActions result = mockMvc.perform(
                delete("/volunteers/posts/{postId}/bookmarks", postId)
        );

        // then
        result.andExpect(status().isNoContent());
        verify(bookmarkService, times(1)).deleteBookmark(anyString(), anyLong());
    }
}
