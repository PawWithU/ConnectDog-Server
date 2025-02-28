package com.pawwithu.connectdog.domain.notification.service;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.notification.dto.response.*;
import com.pawwithu.connectdog.domain.notification.entity.IntermediaryNotification;
import com.pawwithu.connectdog.domain.notification.entity.VolunteerNotification;
import com.pawwithu.connectdog.domain.notification.repository.CustomNotificationRepository;
import com.pawwithu.connectdog.domain.notification.repository.IntermediaryNotificationRepository;
import com.pawwithu.connectdog.domain.notification.repository.VolunteerNotificationRepository;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final VolunteerNotificationRepository volunteerNotificationRepository;

    @Transactional(readOnly = true)
    public List<NotificationsVolunteerGetResponse> getVolunteerNotifications(String email, Pageable pageable) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        List<NotificationVolunteerQueryResponse> queryResponses = customNotificationRepository.getVolunteerNotifications(volunteer.getId(), pageable);
        List<NotificationsVolunteerGetResponse> response = queryResponses.stream().map(NotificationsVolunteerGetResponse::of).toList();
        return response;
    }

    @Transactional(readOnly = true)
    public List<NotificationsIntermediaryGetResponse> getIntermediaryNotifications(String email, Pageable pageable) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        List<NotificationsIntermdiaryQueryResponse> queryResponses = customNotificationRepository.getIntermediaryNotifications(intermediary.getId(), pageable);
        List<NotificationsIntermediaryGetResponse> response = queryResponses.stream().map(NotificationsIntermediaryGetResponse::of).toList();
        return response;
    }

    public NotificationIntermediaryGetOneResponse getIntermediaryOneNotification(String email, Long notificationId) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        IntermediaryNotification notification = intermediaryNotificationRepository.findByIdAndIntermediaryId(notificationId, intermediary.getId()).orElseThrow(() -> new BadRequestException(NOTIFICATION_NOT_FOUND));
        NotificationIntermediaryGetOneResponse response = NotificationIntermediaryGetOneResponse.from(notification);
        notification.updateIsRead();
        return response;
    }

    public NotificationVolunteerGetOneResponse getVolunteerOneNotification(String email, Long notificationId) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        VolunteerNotification notification = volunteerNotificationRepository.findByIdAndVolunteerId(notificationId, volunteer.getId()).orElseThrow(() -> new BadRequestException(NOTIFICATION_NOT_FOUND));
        NotificationVolunteerGetOneResponse response = NotificationVolunteerGetOneResponse.from(notification);
        notification.updateIsRead();
        return response;
    }
}
