package com.pawwithu.connectdog.domain.notification.repository;

import com.pawwithu.connectdog.domain.notification.entity.IntermediaryNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntermediaryNotificationRepository extends JpaRepository<IntermediaryNotification, Long> {
}
