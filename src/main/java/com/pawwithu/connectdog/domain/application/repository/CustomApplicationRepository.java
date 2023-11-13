package com.pawwithu.connectdog.domain.application.repository;

import com.pawwithu.connectdog.domain.application.dto.response.ApplicationWaitingResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomApplicationRepository {

    List<ApplicationWaitingResponse> getWaitingApplications(Long volunteerId, Pageable pageable);

}
