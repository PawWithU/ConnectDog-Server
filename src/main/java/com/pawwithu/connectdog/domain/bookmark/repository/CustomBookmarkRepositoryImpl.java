package com.pawwithu.connectdog.domain.bookmark.repository;

import com.pawwithu.connectdog.domain.volunteer.dto.response.VolunteerGetMyBookmarkResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pawwithu.connectdog.domain.bookmark.entity.QBookmark.bookmark;
import static com.pawwithu.connectdog.domain.intermediary.entity.QIntermediary.intermediary;
import static com.pawwithu.connectdog.domain.post.entity.QPost.post;
import static com.pawwithu.connectdog.domain.post.entity.QPostImage.postImage;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomBookmarkRepositoryImpl implements CustomBookmarkRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<VolunteerGetMyBookmarkResponse> getMyBookmarks(Long volunteerId) {
        return queryFactory
                .select(Projections.constructor(VolunteerGetMyBookmarkResponse.class,
                        post.id, postImage.image, post.departureLoc, post.arrivalLoc, post.startDate, post.endDate,
                        intermediary.name, post.isKennel))
                .from(post)
                .join(post.mainImage, postImage)
                .join(bookmark).on(bookmark.post.id.eq(post.id))
                .where(bookmark.volunteer.id.eq(volunteerId))
                .fetch();
    }
}
