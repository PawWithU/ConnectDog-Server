package com.pawwithu.connectdog.domain.bookmark.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // 공고 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;  // 이동봉사자 id

    @Builder
    public Bookmark(Post post, Volunteer volunteer) {
        this.post = post;
        this.volunteer = volunteer;
    }
}
