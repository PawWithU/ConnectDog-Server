package com.pawwithu.connectdog.domain.notification.repository;

import com.pawwithu.connectdog.domain.notification.entity.VolunteerNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerNotificationRepository extends JpaRepository<VolunteerNotification, Long> {
}
