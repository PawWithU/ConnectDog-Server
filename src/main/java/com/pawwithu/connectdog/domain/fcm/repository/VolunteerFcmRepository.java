package com.pawwithu.connectdog.domain.fcm.repository;

import com.pawwithu.connectdog.domain.fcm.entity.VolunteerFcm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerFcmRepository extends JpaRepository<VolunteerFcm, Long> {
    void deleteByVolunteerId(Long id);
    Optional<VolunteerFcm> findByVolunteerId(Long id);
}
