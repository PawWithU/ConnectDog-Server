package com.pawwithu.connectdog.domain.review.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ReviewImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String image; // 후기 사진
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review; // 후기 id

    @Builder
    public ReviewImage(String image, Review review) {
        this.image = image;
        this.review = review;
    }
}
