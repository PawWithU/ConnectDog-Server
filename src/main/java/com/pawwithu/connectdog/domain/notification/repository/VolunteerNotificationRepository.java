package com.pawwithu.connectdog.domain.notification.repository;

import com.pawwithu.connectdog.domain.notification.entity.VolunteerNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerNotificationRepository extends JpaRepository<VolunteerNotification, Long> {

    Optional<VolunteerNotification> findByIdAndVolunteerId(Long id, Long intermediaryId);
}
