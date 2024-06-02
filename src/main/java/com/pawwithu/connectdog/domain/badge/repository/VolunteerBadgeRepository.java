package com.pawwithu.connectdog.domain.badge.repository;

import com.pawwithu.connectdog.domain.badge.entity.VolunteerBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VolunteerBadgeRepository extends JpaRepository<VolunteerBadge, Long> {
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM VolunteerBadge vb WHERE vb.volunteer.id = :volunteerId")
    void deleteByVolunteerId(@Param("volunteerId") Long volunteerId);
}
