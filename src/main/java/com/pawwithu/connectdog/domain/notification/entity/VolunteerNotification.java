package com.pawwithu.connectdog.domain.notification.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class VolunteerNotification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;  // 이동봉사자 id
    @Column(nullable = false)
    private Boolean isRead;

    @Builder
    public VolunteerNotification(String image, NotificationType notificationType, String title, String body, Volunteer volunteer, Boolean isRead) {
        this.image = image;
        this.notificationType = notificationType;
        this.title = title;
        this.body = body;
        this.volunteer = volunteer;
        this.isRead = isRead;
    }

    public void updateIsRead() { this.isRead = true; }
}
