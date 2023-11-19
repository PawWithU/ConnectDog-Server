package com.pawwithu.connectdog.domain.bookmark.repository;

import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBookmarkResponse;

import java.util.List;

public interface CustomBookmarkRepository {
    List<VolunteerGetMyBookmarkResponse> getMyBookmarks(Long volunteerId);
}
