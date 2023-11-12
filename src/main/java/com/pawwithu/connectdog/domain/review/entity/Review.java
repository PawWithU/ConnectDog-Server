package com.pawwithu.connectdog.domain.review.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.entity.PostImage;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 300, nullable = false)
    private String content; // 후기 내용
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // 공고 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer; // 이동봉사자 id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainImage_id")
    private PostImage mainImage; // 대표 이미지

    @Builder
    public Review(String content, Post post, Volunteer volunteer, PostImage mainImage) {
        this.content = content;
        this.post = post;
        this.volunteer = volunteer;
        this.mainImage = mainImage;
    }

    public void updateMainImage(ReviewImage reviewImage) {
        this.mainImage = mainImage;
    }
}
