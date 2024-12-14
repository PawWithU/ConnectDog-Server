package com.pawwithu.connectdog.domain.post.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import com.pawwithu.connectdog.domain.dog.entity.Dog;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PostStatus status; // 공고 상태
    @Column(length = 20, nullable = false)
    private String departureLoc; // 출발 지역
    @Column(length = 20, nullable = false)
    private String arrivalLoc; // 도착 지역
    @Column(nullable = false)
    private LocalDate startDate; // 봉사 시작 가능 날짜
    @Column(nullable = false)
    private LocalDate endDate; // 봉사 마감 가능 날짜
    @Column(length = 10)
    private String pickUpTime; // 픽업 시간
    @Column(nullable = false)
    private Boolean isKennel; // 컨넬 제공 여부
    @Column(length = 200, nullable = false)
    private String content; // 이동봉사 설명
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainImage_id")
    private PostImage mainImage; // 대표 이미지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intermediary_id", nullable = false)
    private Intermediary intermediary; // 이동봉사 중개 id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id", nullable = false)
    private Dog dog; // 강아지 id
    private LocalDateTime boostDate;  // 끌어올리기 날짜

    @Builder
    public Post(PostStatus status, String departureLoc, String arrivalLoc, LocalDate startDate, LocalDate endDate, String pickUpTime, Boolean isKennel, String content, Intermediary intermediary, Dog dog, LocalDateTime boostDate) {
        this.status = status;
        this.departureLoc = departureLoc;
        this.arrivalLoc = arrivalLoc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickUpTime = pickUpTime;
        this.isKennel = isKennel;
        this.content = content;
        this.intermediary = intermediary;
        this.dog = dog;
        this.boostDate = boostDate;
    }

    public void updateMainImage(PostImage mainImage) {
        this.mainImage = mainImage;
    }

    public void updateStatus(PostStatus status) {
        this.status = status;
    }

    public void updatePost(String departureLoc, String arrivalLoc, LocalDate startDate, LocalDate endDate, String pickUpTime, Boolean isKennel, String content) {
        this.departureLoc = departureLoc;
        this.arrivalLoc = arrivalLoc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickUpTime = pickUpTime;
        this.isKennel = isKennel;
        this.content = content;
    }

    public void updateDeletedIntermediary(Intermediary deletedIntermediary) {
        this.intermediary = deletedIntermediary;
    }

    public void updateBoostDate() { this.boostDate = LocalDateTime.now(); }
}
