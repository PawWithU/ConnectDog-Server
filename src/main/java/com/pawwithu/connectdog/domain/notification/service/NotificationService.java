package com.pawwithu.connectdog.domain.notification.service;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationIntermediaryGetResponse;
import com.pawwithu.connectdog.domain.notification.dto.response.NotificationVolunteerGetResponse;
import com.pawwithu.connectdog.domain.notification.entity.VolunteerNotification;
import com.pawwithu.connectdog.domain.notification.repository.CustomNotificationRepository;
import com.pawwithu.connectdog.domain.notification.repository.VolunteerNotificationRepository;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pawwithu.connectdog.error.ErrorCode.INTERMEDIARY_NOT_FOUND;
import static com.pawwithu.connectdog.error.ErrorCode.VOLUNTEER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final VolunteerRepository volunteerRepository;
    private final IntermediaryRepository intermediaryRepository;
    private final CustomNotificationRepository customNotificationRepository;

    public List<NotificationVolunteerGetResponse> getVolunteerNotifications(String email, Pageable pageable) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        List<NotificationVolunteerGetResponse> response = customNotificationRepository.getVolunteerNotifications(volunteer.getId(), pageable);
        return response;
    }

    public List<NotificationIntermediaryGetResponse> getIntermediaryNotification(String email, Pageable pageable) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        List<NotificationIntermediaryGetResponse> response = customNotificationRepository.getIntermediaryNotifications(intermediary.getId(), pageable);
        return response;
    }
}
