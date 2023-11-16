package com.pawwithu.connectdog.domain.application.repository;

import com.pawwithu.connectdog.domain.application.dto.response.*;
import com.pawwithu.connectdog.domain.application.entity.Application;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomApplicationRepository {

    List<ApplicationVolunteerWaitingResponse> getVolunteerWaitingApplications(Long volunteerId, Pageable pageable);
    List<ApplicationVolunteerProgressingResponse> getVolunteerProgressingApplications(Long volunteerId, Pageable pageable);
    Optional<Application> findByIdAndVolunteerIdWithPost(Long applicationId, Long volunteerId);
    Optional<Application> findByIdAndIntermediaryIdWithPost(Long applicationId, Long intermediaryId);
    List<ApplicationIntermediaryWaitingResponse> getIntermediaryWaitingApplications(Long intermediaryId, Pageable pageable);
    List<ApplicationIntermediaryProgressingResponse> getIntermediaryProgressingApplications(Long intermediaryId, Pageable pageable);
    List<ApplicationVolunteerCompletedResponse> getVolunteerCompletedApplications(Long volunteerId, Pageable pageable);
}
