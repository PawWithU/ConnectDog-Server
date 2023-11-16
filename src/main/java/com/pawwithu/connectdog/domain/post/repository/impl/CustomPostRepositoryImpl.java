package com.pawwithu.connectdog.domain.post.repository.impl;

import com.pawwithu.connectdog.domain.dog.entity.DogSize;
import com.pawwithu.connectdog.domain.intermediary.dto.response.IntermediaryGetPostsResponse;
import com.pawwithu.connectdog.domain.post.dto.request.PostSearchRequest;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetHomeResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostGetOneResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostRecruitingGetResponse;
import com.pawwithu.connectdog.domain.post.dto.response.PostSearchResponse;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import com.pawwithu.connectdog.domain.post.repository.CustomPostRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.pawwithu.connectdog.domain.dog.entity.QDog.dog;
import static com.pawwithu.connectdog.domain.intermediary.entity.QIntermediary.intermediary;
import static com.pawwithu.connectdog.domain.post.entity.QPost.post;
import static com.pawwithu.connectdog.domain.post.entity.QPostImage.postImage;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomPostRepositoryImpl implements CustomPostRepository {

    private final JPAQueryFactory queryFactory;

    // 홈 화면 공고 5개 조회
    @Override
    public List<PostGetHomeResponse> getHomePosts() {
        return queryFactory
                        .select(Projections.constructor(PostGetHomeResponse.class,
                                post.id, postImage.image, post.departureLoc, post.arrivalLoc, post.startDate, post.endDate,
                                intermediary.name, post.isKennel))
                        .from(post)
                        .join(post.intermediary, intermediary)
                        .join(post.mainImage, postImage)
                        .orderBy(post.createdDate.desc())
                        .limit(5)
                        .fetch();
    }

    // 공고 필터 검색
    @Override
    public List<PostSearchResponse> searchPosts(PostSearchRequest request, Pageable pageable) {

        return queryFactory
                .select(Projections.constructor(PostSearchResponse.class,
                        post.id, postImage.image, post.departureLoc, post.arrivalLoc, post.startDate, post.endDate,
                        intermediary.name, post.isKennel))
                .from(post)
                .join(post.intermediary, intermediary)
                .join(post.mainImage, postImage)
                .where(allFilterSearch(request, pageable))
                .orderBy(createOrderSpecifier(request.orderCondition()))
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    // 대표 이미지를 제외한 공고 이미지 조회
    @Override
    public List<String> getOnePostImages(Long postId) {
        return queryFactory
                .select(postImage.image)
                .from(postImage)
                .join(postImage.post, post)
                .where(postImage.post.id.eq(postId)
                        .and(post.mainImage.id.ne(postImage.id)))
                .fetch();
    }

    // 공고 상세 조회 (대표 이미지를 제외한 다른 이미지 포함 X)
    @Override
    public PostGetOneResponse getOnePost(Long volunteerId, Long postId) {
        return queryFactory
                .select(Projections.constructor(PostGetOneResponse.class,
                        post.id, postImage.image, post.status, post.departureLoc, post.arrivalLoc,
                        post.startDate, post.endDate, post.pickUpTime, post.isKennel, post.content,
                        dog.name, dog.size, dog.gender, dog.weight, dog.specifics,
                        intermediary.id, intermediary.profileImage, intermediary.name))
                .from(post)
                .join(post.intermediary, intermediary)
                .join(post.mainImage, postImage)
                .join(post.dog, dog)
                .where(post.id.eq(postId))
                .fetchOne();
    }

    @Override
    public List<PostRecruitingGetResponse> getRecruitingPosts(Long intermediaryId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(PostRecruitingGetResponse.class,
                        post.id, post.status, postImage.image, dog.name, post.startDate, post.endDate,
                        post.departureLoc, post.arrivalLoc))
                .from(post)
                .join(post.mainImage, postImage)
                .join(post.dog, dog)
                .where(post.intermediary.id.eq(intermediaryId)
                        .and(post.status.in(PostStatus.RECRUITING, PostStatus.EXPIRED)))
                .orderBy(post.createdDate.desc())
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();
    }

    @Override
    public List<IntermediaryGetPostsResponse> getIntermediaryPosts(Long intermediaryId, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(IntermediaryGetPostsResponse.class,
                        post.id, postImage.image, post.departureLoc, post.arrivalLoc, post.startDate, post.endDate,
                        intermediary.name, post.isKennel))
                .from(post)
                .join(post.intermediary, intermediary)
                .join(post.mainImage, postImage)
                .where(post.intermediary.id.eq(intermediaryId)
                        .and(post.status.eq(PostStatus.RECRUITING)))    // 모집중인 공고
                .orderBy(post.createdDate.desc())   // 최신순
                .offset(pageable.getOffset())   // 페이지 번호
                .limit(pageable.getPageSize())  // 페이지 사이즈
                .fetch();

    }

    // 봉사 완료 건수
    @Override
    public Long getCountOfCompletedPosts(Long intermediaryId) {
        return queryFactory
                .select(post.count())
                .from(post)
                .where(post.status.eq(PostStatus.COMPLETED)
                        .and(post.intermediary.id.eq(intermediaryId)))
                .fetchOne();
    }

    // 모든 필터 검색
    private BooleanExpression allFilterSearch(PostSearchRequest request, Pageable pageable) {
        return postStatusEq(request.postStatus())
                .and(departureLocContains(request.departureLoc()))
                .and(arrivalLocContains(request.arrivalLoc()))
                .and(dateSearch(request.startDate(), request.endDate()))
                .and(dogSizeEq(request.dogSize()))
                .and(isKennelEq(request.isKennel()))
                .and(intermediaryNameContains(request.intermediaryName()));
    }

    // 공고 상태 필터
    private BooleanExpression postStatusEq(PostStatus postStatus) {
        return postStatus == null ? null : post.status.eq(postStatus);
    }

    // 출발 지역 필터
    private BooleanExpression departureLocContains(String departureLoc) {
        return StringUtils.hasText(departureLoc) ? post.departureLoc.contains(departureLoc) : null;
    }

    // 도착 지역 필터
    private BooleanExpression arrivalLocContains(String arrivalLoc) {
        return StringUtils.hasText(arrivalLoc) ? post.arrivalLoc.contains(arrivalLoc) : null;
    }

    // 일정 필터
    private BooleanExpression dateSearch(LocalDate userStartDate, LocalDate userEndDate) {
        if (userStartDate == null || userEndDate == null) return null;
        return post.startDate.loe(userEndDate).and(post.endDate.goe(userStartDate));
    }

    // 상세 정보 - 강아지 사이즈 필터
    private BooleanExpression dogSizeEq(DogSize dogSize) {
        if (dogSize == null) return null;
        return dog.size.eq(dogSize);
    }

    // 상세 정보 - 켄넬 제공 여부 필터
    private BooleanExpression isKennelEq(Boolean isKennel) {
        if (isKennel == null) return null;
        return post.isKennel.eq(isKennel);
    }

    // 상세 정보 - 이동봉사 중개명 필터
    private BooleanExpression intermediaryNameContains(String intermediaryName) {
        return StringUtils.hasText(intermediaryName) ? post.intermediary.name.contains(intermediaryName) : null;
    }

    // 정렬 필터
    private OrderSpecifier[] createOrderSpecifier(String orderCondition) {
        // default = 마감순 -> 최신순
        OrderSpecifier[] defaultOrder = {
                new OrderSpecifier(Order.ASC, post.endDate),
                new OrderSpecifier(Order.DESC, post.createdDate)
        };

        // 정렬 조건 X: default
        if (!StringUtils.hasText(orderCondition))
            return defaultOrder;
        // "최신순": 최신순 -> 마감순, 나머지: default
        return orderCondition.equals("최신순")
                ? new OrderSpecifier[]{
                        new OrderSpecifier(Order.DESC, post.createdDate),
                        new OrderSpecifier(Order.ASC, post.endDate)}
                : defaultOrder;
    }
}
