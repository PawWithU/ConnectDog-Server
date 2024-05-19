package com.pawwithu.connectdog.domain.volunteer.repository.impl;

import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.CustomVolunteerRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.pawwithu.connectdog.domain.volunteer.entity.QVolunteer.volunteer;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomVolunteerRepositoryImpl implements CustomVolunteerRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Volunteer> getYesterdaySignUpVolunteers(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return queryFactory.selectFrom(volunteer)
                .where(volunteer.createdDate.between(startOfDay, endOfDay))
                .fetch();
    }
}
