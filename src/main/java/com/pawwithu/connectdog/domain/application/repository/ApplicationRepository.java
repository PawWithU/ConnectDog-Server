package com.pawwithu.connectdog.domain.application.repository;

import com.pawwithu.connectdog.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Boolean existsByPostId(Long postId);
    Optional<Application> findByIdAndVolunteerId(Long id, Long volunteerId);
}
