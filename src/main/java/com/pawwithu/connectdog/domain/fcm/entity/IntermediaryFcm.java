package com.pawwithu.connectdog.domain.fcm.entity;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IntermediaryFcm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fcmToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intermediary_id", nullable = false)
    private Intermediary intermediary;

    @Builder
    public IntermediaryFcm(String fcmToken, Intermediary intermediary) {
        this.fcmToken = fcmToken;
        this.intermediary = intermediary;
    }

}
