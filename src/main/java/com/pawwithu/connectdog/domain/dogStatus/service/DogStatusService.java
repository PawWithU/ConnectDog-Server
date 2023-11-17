package com.pawwithu.connectdog.domain.dogStatus.service;

import com.pawwithu.connectdog.common.s3.FileService;
import com.pawwithu.connectdog.domain.dogStatus.dto.request.DogStatusCreateRequest;
import com.pawwithu.connectdog.domain.dogStatus.dto.response.DogStatusGetOneResponse;
import com.pawwithu.connectdog.domain.dogStatus.entity.DogStatus;
import com.pawwithu.connectdog.domain.dogStatus.entity.DogStatusImage;
import com.pawwithu.connectdog.domain.dogStatus.repository.CustomDogStatusRepository;
import com.pawwithu.connectdog.domain.dogStatus.repository.DogStatusImageRepository;
import com.pawwithu.connectdog.domain.dogStatus.repository.DogStatusRepository;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.repository.PostRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.pawwithu.connectdog.error.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DogStatusService {

    private final FileService fileService;
    private final IntermediaryRepository intermediaryRepository;
    private final PostRepository postRepository;
    private final DogStatusRepository dogStatusRepository;
    private final DogStatusImageRepository dogStatusImageRepository;
    private final CustomDogStatusRepository customDogStatusRepository;

    public void createDogStatus(String email, Long postId, DogStatusCreateRequest request, List<MultipartFile> fileList) {

        // 파일이 존재하지 않을 경우
        if (fileList.isEmpty())
            throw new BadRequestException(FILE_NOT_FOUND);

        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException(POST_NOT_FOUND));

        // 근황 저장 (대표 이미지 제외)
        DogStatus dogStatus = DogStatusCreateRequest.dogStatusToEntity(request, intermediary, post);
        DogStatus saveDogStatus = dogStatusRepository.save(dogStatus);

        // 근황 이미지 저장
        List<DogStatusImage> dogStatusImages = fileList.stream()
                .map(f -> DogStatusImage.builder()
                        .image(fileService.uploadFile(f, "intermediary/dogStatus"))
                        .dogStatus(saveDogStatus)
                        .build())
                .collect(Collectors.toList());
        dogStatusImageRepository.saveAll(dogStatusImages);

        // 근황 대표 이미지 업데이트
        dogStatus.updateMainImage(dogStatusImages.get(0));
    }

    @Transactional(readOnly = true)
    public DogStatusGetOneResponse getOneDogStatus(String email, Long dogStatusId) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));

        // 근황 존재 여부 확인
        if (!dogStatusRepository.existsById(dogStatusId)) {
            throw new BadRequestException(DOG_STATUS_NOT_FOUND);
        }

        // 근황 조회 (대표 이미지 포함)
        DogStatusGetOneResponse oneDogStatus = customDogStatusRepository.getOneDogStatus(intermediary.getId(), dogStatusId);
        // 근황 이미지 조회 (대표 이미지 제외)
        List<String> oneDogStatusImages = customDogStatusRepository.getOneDogStatusImages(dogStatusId);
        DogStatusGetOneResponse dogStatus = DogStatusGetOneResponse.of(oneDogStatus, oneDogStatusImages);
        return dogStatus;
    }
}
