package com.pawwithu.connectdog.domain.application.repository;

import com.pawwithu.connectdog.domain.application.entity.Application;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Boolean existsByPostId(Long postId);
    Boolean existsByPostIdAndVolunteerId(Long postId, Long volunteerId);
    Optional<Application> findByIdAndVolunteerId(Long id, Long volunteerId);
    Optional<Application> findByIdAndIntermediaryId(Long id, Long intermediaryId);
    Long countAllByPostId(Long id);
    List<Application> findByVolunteer(Volunteer volunteer);

}
