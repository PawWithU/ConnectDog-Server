package com.pawwithu.connectdog.domain.application.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Application extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private ApplicationStatus status; // 신청 상태
    @Column(length = 10, nullable = false)
    private String volunteerName; // 이동봉사자 이름
    @Column(length = 15, nullable = false)
    private String phone; // 전화번호
    @Column(length = 10, nullable = false)
    private String transportation;  // 교통수단
    @Column(length = 200, nullable = false)
    private String content; // 신청 내용
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // 공고 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intermediary_id", nullable = false)
    private Intermediary intermediary;  // 이동봉사 중개 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;  // 이동봉사자 id

    @Builder
    public Application(ApplicationStatus status, String volunteerName, String phone, String transportation, String content, Post post, Intermediary intermediary, Volunteer volunteer) {
        this.status = status;
        this.volunteerName = volunteerName;
        this.phone = phone;
        this.transportation = transportation;
        this.content = content;
        this.post = post;
        this.intermediary = intermediary;
        this.volunteer = volunteer;
    }

    public void updateStatus(ApplicationStatus status) {
        this.status = status;
    }
}