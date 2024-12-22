package com.pawwithu.connectdog.domain.post.service;

import com.pawwithu.connectdog.common.s3.FileService;
import com.pawwithu.connectdog.domain.application.repository.ApplicationRepository;
import com.pawwithu.connectdog.domain.bookmark.repository.BookmarkRepository;
import com.pawwithu.connectdog.domain.dog.entity.Dog;
import com.pawwithu.connectdog.domain.dog.repository.DogRepository;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.post.dto.request.PostCreateRequest;
import com.pawwithu.connectdog.domain.post.dto.request.PostExtendRequest;
import com.pawwithu.connectdog.domain.post.dto.request.PostSearchRequest;
import com.pawwithu.connectdog.domain.post.dto.request.PostUpdateRequest;
import com.pawwithu.connectdog.domain.post.dto.response.*;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.entity.PostImage;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import com.pawwithu.connectdog.domain.post.repository.CustomPostRepository;
import com.pawwithu.connectdog.domain.post.repository.PostImageRepository;
import com.pawwithu.connectdog.domain.post.repository.PostRepository;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.pawwithu.connectdog.error.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final FileService fileService;
    private final DogRepository dogRepository;
    private final IntermediaryRepository intermediaryRepository;
    private final VolunteerRepository volunteerRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final CustomPostRepository customPostRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ApplicationRepository applicationRepository;

    @CacheEvict(value = "homePosts", key = "'volunteer'", cacheManager = "redisCacheManager")    // 공고 등록 시 홈 화면 공고 조회 캐시 삭제
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

    public void updatePost(String email, Long postId, PostUpdateRequest request, List<MultipartFile> fileList) {

        // 파일이 존재하지 않을 경우
        if (fileList.isEmpty())
            throw new BadRequestException(FILE_NOT_FOUND);

        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        Post post = postRepository.findByIdAndIntermediaryId(postId, intermediary.getId()).orElseThrow(() -> new BadRequestException(POST_NOT_FOUND));

        // 강아지 정보 수정
        Dog dog = post.getDog();
        dog.updateDog(request.dogName(), request.dogSize(), request.specifics());

        // 공고 수정 (대표 이미지 제외)
        post.updatePost(request.departureLoc(), request.arrivalLoc(), request.startDate(), request.endDate(),
                request.pickUpTime(), request.isKennel(), request.content());

        // 공고 이미지 삭제
        postImageRepository.deleteAllByPostId(postId);

        // 공고 이미지 저장
        List<PostImage> postImages = fileList.stream()
                .map(f -> PostImage.builder()
                        .image(fileService.uploadFile(f, "intermediary/post"))
                        .post(post)
                        .build())
                .collect(Collectors.toList());
        postImageRepository.saveAll(postImages);

        // 공고 대표 이미지 업데이트
        post.updateMainImage(postImages.get(0));

    }

    @Transactional(readOnly = true)
    @Cacheable(value = "homePosts", key = "'volunteer'", cacheManager = "redisCacheManager")
    public List<PostGetHomeResponse> getHomePosts() {
        List<PostGetHomeResponse> homePosts = customPostRepository.getHomePosts();
        return homePosts;
    }

    @Transactional(readOnly = true)
    public List<PostSearchResponse> searchPosts(PostSearchRequest request, Pageable pageable) {
        List<PostSearchResponse> searchPosts = customPostRepository.searchPosts(request, pageable);
        return searchPosts;
    }

    @Transactional(readOnly = true)
    public PostVolunteerGetOneResponse getVolunteerOnePost(String email, Long postId) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        // 공고 존재 여부 확인
        if (!postRepository.existsById(postId)) {
            throw new BadRequestException(POST_NOT_FOUND);
        }
        // 공고 조회 (대표 이미지 포함)
        PostVolunteerGetOneResponse onePost = customPostRepository.getVolunteerOnePost(postId);
        // 공고 이미지 조회 (대표 이미지 제외)
        List<String> onePostImages = customPostRepository.getOnePostImages(postId);
        // 북마크 여부
        Boolean isBookmark = bookmarkRepository.existsByVolunteerIdAndPostId(volunteer.getId(), postId);
        PostVolunteerGetOneResponse response = PostVolunteerGetOneResponse.of(onePost, onePostImages, isBookmark);
        return response;
    }

    @Transactional(readOnly = true)
    public List<PostRecruitingGetResponse> getRecruitingPosts(String email, Pageable pageable) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        List<PostRecruitingGetResponse> recruitingPosts = customPostRepository.getRecruitingPosts(intermediary.getId(), pageable);
        return recruitingPosts;
    }

    @CacheEvict(value = "homePosts", key = "'volunteer'", cacheManager = "redisCacheManager")    // 공고 삭제 시 홈 화면 공고 조회 캐시 삭제
    public void deletePost(String email, Long postId) {
        // 이동봉사 중개
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        // 공고
        Post post = postRepository.findByIdAndIntermediaryIdAndStatus(postId, intermediary.getId(), PostStatus.RECRUITING).orElseThrow(() -> new BadRequestException(POST_NOT_FOUND));
        // 신청, 공고 이미지, 공고, 강아지 삭제
        applicationRepository.deleteAllByPostId(postId);
        postImageRepository.deleteAllByPostId(postId);
        postRepository.deleteById(post.getId());
        dogRepository.deleteById(post.getDog().getId());
    }

    @Transactional(readOnly = true)
    public PostIntermediaryGetOneResponse getIntermediaryOnePost(String email, Long postId) {
        // 공고 존재 여부 확인
        if (!postRepository.existsById(postId)) {
            throw new BadRequestException(POST_NOT_FOUND);
        }
        // 공고 조회 (대표 이미지 포함)
        PostIntermediaryGetOneResponse onePost = customPostRepository.getIntermediaryOnePost(postId);
        // 공고 이미지 조회 (대표 이미지 제외)
        List<String> onePostImages = customPostRepository.getOnePostImages(postId);
        PostIntermediaryGetOneResponse response = PostIntermediaryGetOneResponse.of(onePost, onePostImages);
        return response;
    }

    public void boostPost(String email, Long postId) {
        // 이동봉사 중개
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        // 공고
        Post post = postRepository.findByIdAndIntermediaryIdAndStatus(postId, intermediary.getId(), PostStatus.RECRUITING).orElseThrow(() -> new BadRequestException(POST_NOT_FOUND));
        LocalDateTime now = LocalDateTime.now();
        // 공고 끌어올린 시점에서 48시간이 지나지 않았다면 exception
        if (now.isBefore(post.getBoostDate().plusHours(48))) {
            throw new BadRequestException(INVALID_BOOST_REQUEST);
        }
        post.updateBoostDate();
    }

    public void extendPost(String email, Long postId, PostExtendRequest request) {
        // 이동봉사 중개
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        // 공고
        Post post = postRepository.findByIdAndIntermediaryIdAndStatus(postId, intermediary.getId(), PostStatus.EXPIRED).orElseThrow(() -> new BadRequestException(POST_NOT_FOUND));
        LocalDate now = LocalDate.now();
        if (now.isAfter(request.endDate()) || request.endDate().isBefore(request.startDate())) {
            throw new BadRequestException(INVALID_POST_DATE);
        }
        // startDate, endDate, pickUpTime 업데이트 및 공고 상태 모집 마감 -> 모집중 변경
        post.extendDate(request.startDate(), request.endDate(), request.pickUpTime());
    }
}
