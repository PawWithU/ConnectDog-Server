package com.pawwithu.connectdog.domain.badge.repository.impl;

import com.pawwithu.connectdog.domain.badge.repository.CustomVolunteerBadgeRepository;
import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBadgeResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pawwithu.connectdog.domain.badge.entity.QBadge.badge;
import static com.pawwithu.connectdog.domain.badge.entity.QVolunteerBadge.volunteerBadge;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomVolunteerBadgeRepositoryImpl implements CustomVolunteerBadgeRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<VolunteerGetMyBadgeResponse> getMyBadges(Long volunteerId) {
        return queryFactory
                .select(Projections.constructor(VolunteerGetMyBadgeResponse.class,
                        badge.id,
                        badge.name,
                        new CaseBuilder()
                                .when(volunteerBadge.id.isNotNull())
                                .then(badge.image)
                                .otherwise((String) null)))
                .from(badge)
                .leftJoin(volunteerBadge)
                .on(volunteerBadge.badge.id.eq(badge.id)
                        .and(volunteerBadge.volunteer.id.eq(volunteerId)))
                .fetch();
    }
}
