package com.pawwithu.connectdog.domain.notification.service;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationIntermediaryGetOneResponse;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationsIntermediaryGetResponse;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationsVolunteerGetResponse;
import com.pawwithu.connectdog.domain.notification.entity.IntermediaryNotification;
import com.pawwithu.connectdog.domain.notification.repository.CustomNotificationRepository;
import com.pawwithu.connectdog.domain.notification.repository.IntermediaryNotificationRepository;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pawwithu.connectdog.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final VolunteerRepository volunteerRepository;
    private final IntermediaryRepository intermediaryRepository;
    private final CustomNotificationRepository customNotificationRepository;
    private final IntermediaryNotificationRepository intermediaryNotificationRepository;

    @Transactional(readOnly = true)
    public List<NotificationsVolunteerGetResponse> getVolunteerNotifications(String email, Pageable pageable) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        List<NotificationsVolunteerGetResponse> response = customNotificationRepository.getVolunteerNotifications(volunteer.getId(), pageable);
        return response;
    }

    @Transactional(readOnly = true)
    public List<NotificationsIntermediaryGetResponse> getIntermediaryNotifications(String email, Pageable pageable) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        List<NotificationsIntermediaryGetResponse> response = customNotificationRepository.getIntermediaryNotifications(intermediary.getId(), pageable);
        return response;
    }

    public NotificationIntermediaryGetOneResponse getIntermediaryOneNotification(String email, Long notificationId) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        IntermediaryNotification notification = intermediaryNotificationRepository.findByIdAndIntermediaryId(notificationId, intermediary.getId()).orElseThrow(() -> new BadRequestException(NOTIFICATION_NOT_FOUND));
        NotificationIntermediaryGetOneResponse response = NotificationIntermediaryGetOneResponse.from(notification);
        notification.updateIsRead();
        return response;
    }
}
