package com.pawwithu.connectdog.domain.notification.entity;

import com.pawwithu.connectdog.common.entity.BaseTimeEntity;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class IntermediaryNotification extends BaseTimeEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private NotificationType notificationType;
        @Column(nullable = false)
        private String title;
        @Column(nullable = false)
        private String body;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "intermediary_id", nullable = false)
        private Intermediary intermediary;  // 이동봉사 중개 id
        @Column(nullable = false)
        private Boolean isRead;

        @Builder
    public IntermediaryNotification(NotificationType notificationType, String title, String body, Intermediary intermediary, Boolean isRead) {
        this.notificationType = notificationType;
        this.title = title;
        this.body = body;
        this.intermediary = intermediary;
        this.isRead = isRead;
    }

    public void updateIsRead() { this.isRead = true; }
}
