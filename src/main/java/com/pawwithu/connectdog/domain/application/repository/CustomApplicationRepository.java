package com.pawwithu.connectdog.domain.application.repository;

import com.pawwithu.connectdog.domain.application.dto.response.ApplicationVolunteerProgressingResponse;
import com.pawwithu.connectdog.domain.application.dto.response.ApplicationVolunteerWaitingResponse;
import com.pawwithu.connectdog.domain.application.entity.Application;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomApplicationRepository {

    List<ApplicationVolunteerWaitingResponse> getVolunteerWaitingApplications(Long volunteerId, Pageable pageable);
    List<ApplicationVolunteerProgressingResponse> getVolunteerProgressingApplications(Long volunteerId, Pageable pageable);
    Optional<Application> findByIdAndVolunteerIdWithPost(Long applicationId, Long volunteerId);
    Optional<Application> findByIdAndIntermediaryIdWithPost(Long applicationId, Long intermediaryId);
}
