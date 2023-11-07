package com.pawwithu.connectdog.domain.post.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import com.pawwithu.connectdog.domain.dog.entity.Dog;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PostStatus status; // 공고 상태
    @Column(length = 20, nullable = false)
    private String departureLoc; // 출발 지역
    @Column(length = 20, nullable = false)
    private String arrivalLoc; // 도착 지역
    @Column(nullable = false)
    private LocalDateTime startDate; // 봉사 시작 가능 날짜
    @Column(nullable = false)
    private LocalDateTime endDate; // 봉사 마감 가능 날짜
    @Column(length = 10)
    private String pickUpTime; // 픽업 시간
    @Column(nullable = false)
    private Boolean isKennel; // 컨넬 제공 여부
    @Column(length = 200, nullable = false)
    private String content; // 이동봉사 설명
    @Column(nullable = false)
    private String mainImage; // 대표 이미지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intermediary_id", nullable = false)
    private Intermediary intermediary; // 이동봉사 중개 id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id", nullable = false)
    private Dog dog; // 강아지 id

}
