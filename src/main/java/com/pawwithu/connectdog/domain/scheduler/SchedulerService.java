package com.pawwithu.connectdog.domain.scheduler;

import com.pawwithu.connectdog.domain.application.entity.Application;
import com.pawwithu.connectdog.domain.application.repository.CustomApplicationRepository;
import com.pawwithu.connectdog.domain.fcm.entity.VolunteerFcm;
import com.pawwithu.connectdog.domain.fcm.repository.VolunteerFcmRepository;
import com.pawwithu.connectdog.domain.fcm.service.FcmService;
import com.pawwithu.connectdog.domain.notification.entity.NotificationType;
import com.pawwithu.connectdog.domain.post.repository.CustomPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.pawwithu.connectdog.domain.fcm.dto.NotificationMessage.REJECT;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SchedulerService {

    private final CustomPostRepository customPostRepository;
    private final CustomApplicationRepository customApplicationRepository;
    private final FcmService fcmService;
    private final VolunteerFcmRepository volunteerFcmRepository;

    // 매일 00시 - 공고 모집 마감 업데이트, 신청 자동 반려
    @Scheduled(cron = "0 0 0 * * *")
    public void updateExpiredPostsAndApplications() {
        LocalDate today = LocalDate.now();
        // 공고 모집 마감 업데이트
        customPostRepository.updateExpiredPosts(today);
        // 신청 자동 반려
        customApplicationRepository.updateExpiredApplications(today);
    }

    // 매일 9시 - 모집 마감된 공고를 신청했던 봉사자에게 반려 알림 전송
    @Scheduled(cron = "0 0 9 * * *")
    public void sendRejectNotification() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        List<Application> applications = customApplicationRepository.getYesterdayExpiredApplications(yesterday);
        for (Application application : applications) {
            VolunteerFcm volunteerFcm = volunteerFcmRepository.findByVolunteerId(application.getVolunteer().getId()).orElse(null);
            if (volunteerFcm != null) {
                fcmService.sendMessageToVolunteer(volunteerFcm.getFcmToken(), application.getVolunteer(),
                        application.getPost().getMainImage().getImage(), NotificationType.REJECTED, REJECT.getTitle(), REJECT.getBody());
            } else {
                log.info("----------모집 마감 공고 신청 반려 알림 전송 실패----------");
            }
        }
    }

}