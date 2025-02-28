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
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.CustomVolunteerRepository;
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
    private final CustomVolunteerRepository customVolunteerRepository;

    // 매일 00시 - 공고 모집 마감 업데이트, 신청 자동 반려
    @Scheduled(cron = "0 0 0 * * *")
    public void updateExpiredPostsAndApplications() {
        log.info("----------00시 [공고 모집 마감 스케줄링] start----------");
        LocalDate today = LocalDate.now();
        // 공고 모집 마감 업데이트
        customPostRepository.updateExpiredPosts(today);
        // 신청 자동 반려
        customApplicationRepository.updateExpiredApplications(today);
        log.info("----------00시 [공고 모집 마감 스케줄링] end----------");
    }

    // 매일 9시 - 모집 마감된 공고를 신청했던 봉사자에게 반려 알림 전송 및 모집자에게 공고 모집 기간 만료 알림 전송
    @Scheduled(cron = "0 0 9 * * *")
    public void sendRejectNotification() {
        log.info("----------9시 [공고 모집 마감 알림 전송] start----------");
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        // 모집 마감된 공고를 신청했던 봉사자에게 반려 알림 전송
        List<Application> applications = customApplicationRepository.getYesterdayExpiredApplications(yesterday);
        for (Application application : applications) {
            VolunteerFcm volunteerFcm = volunteerFcmRepository.findByVolunteerId(application.getVolunteer().getId()).orElse(null);
            if (volunteerFcm != null) {
                fcmService.sendMessageToVolunteer(volunteerFcm.getFcmToken(), application.getVolunteer(),
                        application.getPost().getMainImage().getImage(), NotificationType.REJECTED, EXPIRED_REJECT.getTitle(), EXPIRED_REJECT.getBody());
            } else {
                log.info("----------모집 마감 공고 신청 반려 알림 전송 실패----------");
            }
        }
        // 모집자에게 모집 기간 만료 알림 전송
        List<Post> posts = customPostRepository.getYesterdayExpiredPosts(yesterday);
        for (Post post : posts) {
            IntermediaryFcm intermediaryFcm = intermediaryFcmRepository.findByIntermediaryId(post.getIntermediary().getId()).orElse(null);
            if (intermediaryFcm != null) {
                fcmService.sendMessageToIntermediary(intermediaryFcm.getFcmToken(), post.getIntermediary(),
                        NotificationType.EXPIRED, EXPIRED.getTitle(), EXPIRED.getBody());
            } else {
                log.info("----------공고 마감 사전 알림 전송 실패----------");
            }
        }
        log.info("----------9시 [공고 모집 마감 알림 전송] end----------");

    }

    // 매일 오후 12시 - 공고 모집 마감 알림 12시간 전 알림
    @Scheduled(cron = "0 0 12 * * *")
    public void sendBeforeExpiredNotification() {
        log.info("----------12시 [공고 모집 마감 12시간 전 알림 전송] start----------");
        LocalDate today = LocalDate.now();
        // 모집 마감 하루 전 모집중 공고 알림 전송
        List<Post> recruitingPosts = customPostRepository.getBeforeExpiredRecruitingPosts(today);
        for (Post post : recruitingPosts) {
            IntermediaryFcm intermediaryFcm = intermediaryFcmRepository.findByIntermediaryId(post.getIntermediary().getId()).orElse(null);
            if (intermediaryFcm != null) {
                fcmService.sendMessageToIntermediary(intermediaryFcm.getFcmToken(), post.getIntermediary(),
                        NotificationType.BEFORE_EXPIRED, BEFORE_EXPIRED.getTitle(), BEFORE_EXPIRED.getBodyWithContent("\n아직 봉사자를 구하지 못했다면 기간을 조정해보세요!"));
            } else {
                log.info("----------공고 마감 사전 알림 전송 실패----------");
            }
        }
        // 모집 마감 하루 전 승인대기중 공고 알림 전송
        List<Post> waitingPosts = customPostRepository.getBeforeExpiredWaitingPosts(today);
        for (Post post : waitingPosts) {
            IntermediaryFcm intermediaryFcm = intermediaryFcmRepository.findByIntermediaryId(post.getIntermediary().getId()).orElse(null);
            if (intermediaryFcm != null) {
                fcmService.sendMessageToIntermediary(intermediaryFcm.getFcmToken(), post.getIntermediary(),
                        NotificationType.BEFORE_EXPIRED, BEFORE_EXPIRED.getTitle(), BEFORE_EXPIRED.getBodyWithContent("\n신청자가 있으니 빠르게 확인해주세요!"));
            } else {
                log.info("----------공고 마감 사전 알림 전송 실패----------");
            }
        }
        log.info("----------12시 [공고 모집 마감 12시간 전 알림 전송] end----------");
    }

    // 매일 오전 10시 - 이동봉사 진행 완료 요청 알림
    @Scheduled(cron = "0 0 10 * * *")
    public void sendCompleteRequestNotification() {
        log.info("----------10시 [이동봉사 진행 완료 요청 알림 전송] start----------");
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        List<Application> applications = customApplicationRepository.getExpiredProgressingPosts(yesterday);
        for (Application application : applications) {
            IntermediaryFcm intermediaryFcm = intermediaryFcmRepository.findByIntermediaryId(application.getPost().getIntermediary().getId()).orElse(null);
            if (intermediaryFcm != null) {
                fcmService.sendMessageToIntermediary(intermediaryFcm.getFcmToken(), application.getIntermediary(),
                        NotificationType.COMPLETED_REQUEST, COMPLETED_REQUEST.getTitle(), COMPLETED_REQUEST.getBody());
            } else {
                log.info("----------이동봉사 진행 완료 요청 알림 전송 실패----------");
            }
        }
        log.info("----------10시 [이동봉사 진행 완료 요청 알림 전송] end----------");
    }

    // 매일 15시 - 이동봉사 가이드 알림
    @Scheduled(cron = "0 0 15 * * *")
    public void sendGuideNotification() {
        log.info("----------15시 [이동봉사 가이드 알림 전송] start----------");
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        List<Volunteer> volunteers = customVolunteerRepository.getYesterdaySignUpVolunteers(yesterday);
        for (Volunteer volunteer : volunteers) {
            VolunteerFcm volunteerFcm = volunteerFcmRepository.findByVolunteerId(volunteer.getId()).orElse(null);
            if (volunteerFcm != null) {
                fcmService.sendMessageToVolunteer(volunteerFcm.getFcmToken(), volunteer,
                        volunteer.getProfileImageNum() + "", NotificationType.GUIDE, GUIDE.getTitle(), GUIDE.getBody());
            } else {
                log.info("----------이동봉사 가이드 알림 전송 실패----------");
            }
        }
        log.info("----------15시 [이동봉사 가이드 알림 전송] end----------");
    }
}