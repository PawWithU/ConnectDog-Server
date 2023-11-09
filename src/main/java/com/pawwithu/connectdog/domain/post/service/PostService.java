package com.pawwithu.connectdog.domain.post.service;

import com.pawwithu.connectdog.common.s3.FileService;
import com.pawwithu.connectdog.domain.dog.entity.Dog;
import com.pawwithu.connectdog.domain.dog.repository.DogRepository;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.post.dto.request.PostCreateRequest;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetHomeResponse;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.entity.PostImage;
import com.pawwithu.connectdog.domain.post.repository.CustomPostRepository;
import com.pawwithu.connectdog.domain.post.repository.PostImageRepository;
import com.pawwithu.connectdog.domain.post.repository.PostRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.pawwithu.connectdog.error.ErrorCode.FILE_NOT_FOUND;
import static com.pawwithu.connectdog.error.ErrorCode.INTERMEDIARY_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final FileService fileService;
    private final DogRepository dogRepository;
    private final IntermediaryRepository intermediaryRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final CustomPostRepository customPostRepository;

    public void createPost(String email, PostCreateRequest request, List<MultipartFile> fileList) {

        // 파일이 존재하지 않을 경우
        if (fileList.isEmpty())
            throw new BadRequestException(FILE_NOT_FOUND);

        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));

        // 강아지 저장
        Dog dog = dogRepository.save(request.dogToEntity());

        // 공고 저장 (대표 이미지 제외)
        Post post = PostCreateRequest.postToEntity(request, dog, intermediary);
        Post savePost = postRepository.save(post);

        // 공고 이미지 저장
        List<PostImage> postImages = fileList.stream()
                .map(f -> PostImage.builder()
                        .image(fileService.uploadFile(f, "intermediary/post"))
                        .post(savePost)
                        .build())
                .collect(Collectors.toList());
        postImageRepository.saveAll(postImages);

        // 공고 대표 이미지 업데이트
        post.updateMainImage(postImages.get(0));

    }

    public List<PostGetHomeResponse> getHomePosts() {
        List<PostGetHomeResponse> homePosts = customPostRepository.getHomePosts();
        return homePosts;
    }
}