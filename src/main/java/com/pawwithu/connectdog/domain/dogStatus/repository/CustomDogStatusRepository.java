package com.pawwithu.connectdog.domain.dogStatus.repository;

import com.pawwithu.connectdog.domain.dogStatus.dto.response.DogStatusGetOneResponse;

import java.util.List;

public interface CustomDogStatusRepository {

    // 대표 이미지를 제외한 근황 이미지 조회
    List<String> getOneDogStatusImages(Long dogStatusId);
    // 근황 단건 조회 (대표 이미지를 제외한 다른 이미지 포함 X)
    DogStatusGetOneResponse getOneDogStatus(Long id, Long dogStatusId);
}