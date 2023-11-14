package com.pawwithu.connectdog.domain.dogStatus.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class DogStatus extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200, nullable = false)
    private String content;     // 근황 내용
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // 공고 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intermediary_id", nullable = false)
    private Intermediary intermediary;  // 이동봉사 중개 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainImage_id")
    private DogStatusImage mainImage; // 대표 이미지

    @Builder
    public DogStatus(String content, Post post, Intermediary intermediary) {
        this.content = content;
        this.post = post;
        this.intermediary = intermediary;
    }

    public void updateMainImage(DogStatusImage mainImage) {
        this.mainImage = mainImage;
    }
}
