package com.pawwithu.connectdog.domain.post.repository.impl;

import com.pawwithu.connectdog.domain.post.dto.response.PostGetHomeResponse;
import com.pawwithu.connectdog.domain.post.repository.CustomPostRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pawwithu.connectdog.domain.intermediary.entity.QIntermediary.intermediary;
import static com.pawwithu.connectdog.domain.post.entity.QPost.post;
import static com.pawwithu.connectdog.domain.post.entity.QPostImage.postImage;

@Repository
@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostGetHomeResponse> getHomePosts() {
        return queryFactory
                        .select(Projections.constructor(PostGetHomeResponse.class,
                                postImage.image, post.departureLoc, post.arrivalLoc, post.startDate, post.endDate,
                                intermediary.name, post.isKennel))
                        .from(post)
                        .join(post.intermediary, intermediary)
                        .join(post.mainImage, postImage)
                        .orderBy(post.createdDate.desc())
                        .limit(5)
                        .fetch();
    }

}
