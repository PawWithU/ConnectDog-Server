package com.pawwithu.connectdog.domain.scheduler;

import com.pawwithu.connectdog.domain.application.entity.Application;
import com.pawwithu.connectdog.domain.application.repository.CustomApplicationRepository;
import com.pawwithu.connectdog.domain.fcm.entity.IntermediaryFcm;
import com.pawwithu.connectdog.domain.fcm.entity.VolunteerFcm;
import com.pawwithu.connectdog.domain.fcm.repository.IntermediaryFcmRepository;
import com.pawwithu.connectdog.domain.fcm.repository.VolunteerFcmRepository;
import com.pawwithu.connectdog.domain.fcm.service.FcmService;
import com.pawwithu.connectdog.domain.notification.entity.NotificationType;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.repository.CustomPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.pawwithu.connectdog.domain.fcm.dto.NotificationMessage.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SchedulerService {

    private final CustomPostRepository customPostRepository;
    private final CustomApplicationRepository customApplicationRepository;
    private final FcmService fcmService;
    private final VolunteerFcmRepository volunteerFcmRepository;
    private final IntermediaryFcmRepository intermediaryFcmRepository;

    // 매일 00시 - 공고 모집 마감 업데이트, 신청 자동 반려
    @Scheduled(cron = "0 0 0 * * *")
    public void updateExpiredPostsAndApplications() {
        LocalDate today = LocalDate.now();
        // 공고 모집 마감 업데이트
        customPostRepository.updateExpiredPosts(today);
        // 신청 자동 반려
        customApplicationRepository.updateExpiredApplications(today);
    }

    // 매일 9시 - 모집 마감된 공고를 신청했던 봉사자에게 반려 알림 전송 및 모집자에게 공고 모집 기간 만료 알림 전송
    @Scheduled(cron = "0 0 9 * * *")
    public void sendRejectNotification() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        // 모집 마감된 공고를 신청했던 봉사자에게 반려 알림 전송
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
        // 모집자에게 모집 기간 만료 알림 전송
        List<Post> posts = customPostRepository.getYesterdayExpiredPosts(yesterday);
        for (Post post : posts) {
            IntermediaryFcm intermediaryFcm = intermediaryFcmRepository.findByIntermediaryId(post.getIntermediary().getId()).orElse(null);
            if (intermediaryFcm != null) {
                fcmService.sendMessageToIntermediary(intermediaryFcm.getFcmToken(), post.getIntermediary(), post.getMainImage().getImage(),
                        NotificationType.EXPIRED, EXPIRED.getTitle(), EXPIRED.getBody());
            } else {
                log.info("----------공고 마감 사전 알림 전송 실패----------");
            }
        }

    }

    // 매일 오후 12시 - 공고 모집 마감 알림 12시간 전 알림
    @Scheduled(cron = "0 0 12 * * *")
    public void sendBeforeExpiredNotification() {
        LocalDate today = LocalDate.now();
        // 모집 마감 하루 전 모집중 공고 알림 전송
        List<Post> recruitingPosts = customPostRepository.getBeforeExpiredRecruitingPosts(today);
        for (Post post : recruitingPosts) {
            IntermediaryFcm intermediaryFcm = intermediaryFcmRepository.findByIntermediaryId(post.getIntermediary().getId()).orElse(null);
            if (intermediaryFcm != null) {
                fcmService.sendMessageToIntermediary(intermediaryFcm.getFcmToken(), post.getIntermediary(), post.getMainImage().getImage(),
                        NotificationType.BEFORE_EXPIRED, BEFORE_EXPIRED.getTitle(), BEFORE_EXPIRED.getBodyWithContent(" 아직 봉사자를 구하지 못했다면 기간을 조정해보세요!"));
            } else {
                log.info("----------공고 마감 사전 알림 전송 실패----------");
            }
        }
        // 모집 마감 하루 전 승인대기중 공고 알림 전송
        List<Post> waitingPosts = customPostRepository.getBeforeExpiredWaitingPosts(today);
        for (Post post : waitingPosts) {
            IntermediaryFcm intermediaryFcm = intermediaryFcmRepository.findByIntermediaryId(post.getIntermediary().getId()).orElse(null);
            if (intermediaryFcm != null) {
                fcmService.sendMessageToIntermediary(intermediaryFcm.getFcmToken(), post.getIntermediary(), post.getMainImage().getImage(),
                        NotificationType.BEFORE_EXPIRED, BEFORE_EXPIRED.getTitle(), BEFORE_EXPIRED.getBodyWithContent(" 신청자가 있으니 빠르게 확인해주세요!"));
            } else {
                log.info("----------공고 마감 사전 알림 전송 실패----------");
            }
        }
    }
}