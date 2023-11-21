package com.pawwithu.connectdog.domain.fcm.repository;

import com.pawwithu.connectdog.domain.fcm.entity.VolunteerFcm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerFcmRepository extends JpaRepository<VolunteerFcm, Long> {
    void deleteByVolunteerId(Long id);
}
