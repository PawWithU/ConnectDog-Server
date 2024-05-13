package com.pawwithu.connectdog.domain.fcm.repository;

import com.pawwithu.connectdog.domain.fcm.entity.IntermediaryFcm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntermediaryFcmRepository extends JpaRepository<IntermediaryFcm, Long> {
    void deleteByIntermediaryId(Long id);
    Optional<IntermediaryFcm> findByIntermediaryId(Long id);
}
