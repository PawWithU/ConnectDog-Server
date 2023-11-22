package com.pawwithu.connectdog.domain.application.repository;

import com.pawwithu.connectdog.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Boolean existsByPostId(Long postId);
    Boolean existsByPostIdAndVolunteerId(Long postId, Long volunteerId);
    Optional<Application> findByIdAndVolunteerId(Long id, Long volunteerId);
    Optional<Application> findByIdAndIntermediaryId(Long id, Long intermediaryId);
}
