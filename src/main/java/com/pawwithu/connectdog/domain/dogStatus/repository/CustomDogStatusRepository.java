package com.pawwithu.connectdog.domain.dogStatus.repository;

import com.pawwithu.connectdog.domain.dogStatus.dto.response.DogStatusGetOneResponse;
import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetDogStatusesResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomDogStatusRepository {

    // 대표 이미지를 제외한 근황 이미지 조회
    List<String> getOneDogStatusImages(Long dogStatusId);

    // 근황 단건 조회 (대표 이미지를 제외한 다른 이미지 포함 X)
    DogStatusGetOneResponse getOneDogStatus(Long dogStatusId);

    // 이동봉사 중개 별 근황 조회 (최신순)
    List<IntermediaryGetDogStatusesResponse> getIntermediaryDogStatuses(Long intermediaryId, Pageable pageable);

    // 남긴 근황 총 건수
    Long getIntermediaryCountOfDogStatuses(Long intermediaryId);

    // 입양 근황 건수
    Long getVolunteerCountOfDogStatuses(Long id);
}
