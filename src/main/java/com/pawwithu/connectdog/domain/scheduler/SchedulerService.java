package com.pawwithu.connectdog.domain.scheduler;

import com.pawwithu.connectdog.domain.application.repository.CustomApplicationRepository;
import com.pawwithu.connectdog.domain.fcm.repository.VolunteerFcmRepository;
import com.pawwithu.connectdog.domain.fcm.service.FcmService;
import com.pawwithu.connectdog.domain.post.repository.CustomPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SchedulerService {

    private final CustomPostRepository customPostRepository;
    private final CustomApplicationRepository customApplicationRepository;
    private final FcmService fcmService;
    private final VolunteerFcmRepository volunteerFcmRepository;
    
    @Scheduled(cron = "0 0 0 * * *")	// 매일 00시 정각
    public void updateExpiredPostsAndApplications() {
        LocalDate today = LocalDate.now();
        // 모집 마감 공고 업데이트
        customPostRepository.updateExpiredPosts(today);
        // 모집 마감 신청 업데이트
        customApplicationRepository.updateExpiredApplications(today);
    }



}