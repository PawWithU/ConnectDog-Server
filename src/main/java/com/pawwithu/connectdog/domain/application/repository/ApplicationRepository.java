package com.pawwithu.connectdog.domain.application.repository;

import com.pawwithu.connectdog.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Boolean existsByPostId(Long postId);
}
