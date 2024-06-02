package com.pawwithu.connectdog.domain.badge.repository;

import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBadgeResponse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomVolunteerBadgeRepository {
    List<VolunteerGetMyBadgeResponse> getMyBadges(Long id);
}
