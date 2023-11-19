package com.pawwithu.connectdog.domain.badge.repository;

import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBadgeResponse;

import java.util.List;

public interface CustomVolunteerBadgeRepository {
    List<VolunteerGetMyBadgeResponse> getMyBadges(Long id);
}
