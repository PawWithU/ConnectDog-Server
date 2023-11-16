package com.pawwithu.connectdog.domain.intermediary.service;

import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetPostsResponse;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.post.repository.CustomPostRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pawwithu.connectdog.error.ErrorCode.INTERMEDIARY_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IntermediaryService {

    private final IntermediaryRepository intermediaryRepository;
    private final CustomPostRepository customPostRepository;

    public List<IntermediaryGetPostsResponse> getIntermediaryPosts(Long intermediaryId, Pageable pageable) {
        // 이동봉사 중개
        if (!intermediaryRepository.existsById(intermediaryId)){
            throw new BadRequestException(INTERMEDIARY_NOT_FOUND);
        }
        List<IntermediaryGetPostsResponse> intermediaryPosts = customPostRepository.getIntermediaryPosts(intermediaryId, pageable);
        return intermediaryPosts;
    }
}
