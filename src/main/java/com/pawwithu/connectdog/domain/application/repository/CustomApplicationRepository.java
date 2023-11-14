package com.pawwithu.connectdog.domain.application.repository;

import com.pawwithu.connectdog.domain.application.dto.response.ApplicationProgressingResponse;
import com.pawwithu.connectdog.domain.application.dto.response.ApplicationWaitingResponse;
import com.pawwithu.connectdog.domain.application.entity.Application;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomApplicationRepository {

    List<ApplicationWaitingResponse> getWaitingApplications(Long volunteerId, Pageable pageable);
    List<ApplicationProgressingResponse> getProgressingApplications(Long volunteerId, Pageable pageable);
    Optional<Application> findByIdAndVolunteerIdWithPost(Long applicationId, Long volunteerId);
}
