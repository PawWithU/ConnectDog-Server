package com.pawwithu.connectdog.domain.notification.repository;

import com.pawwithu.connectdog.domain.notification.entity.IntermediaryNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntermediaryNotificationRepository extends JpaRepository<IntermediaryNotification, Long> {

    Optional<IntermediaryNotification> findByIdAndIntermediaryId(Long id, Long intermediaryId);

    void deleteByIntermediaryId(Long id);
}
