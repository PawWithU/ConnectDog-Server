package com.pawwithu.connectdog.domain.volunteer.repository;

import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;

import java.time.LocalDate;
import java.util.List;

public interface CustomVolunteerRepository {

    List<Volunteer> getYesterdaySignUpVolunteers(LocalDate date);
}
