package com.pawwithu.connectdog.domain.fcm.entity;

import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class VolunteerFcm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fcmToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;

    @Builder
    public VolunteerFcm(String fcmToken, Volunteer volunteer) {
        this.fcmToken = fcmToken;
        this.volunteer = volunteer;
    }

}
