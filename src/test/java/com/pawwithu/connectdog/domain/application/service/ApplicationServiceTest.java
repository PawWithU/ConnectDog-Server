package com.pawwithu.connectdog.domain.application.service;

import com.pawwithu.connectdog.domain.application.dto.request.VolunteerApplyRequest;
import com.pawwithu.connectdog.domain.application.repository.ApplicationRepository;
import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.entity.VolunteerRole;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class ApplicationServiceTest {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private VolunteerRepository volunteerRepository;
    @Autowired
    private ApplicationService applicationService;

    @Test
    public void test_concurrency() throws InterruptedException {
        // 이동봉사자 정보 설정
        Volunteer volunteer1 = new Volunteer("example1@example.com", VolunteerRole.AUTH_VOLUNTEER, SocialType.NAVER, "자동차");
        Volunteer volunteer2 = new Volunteer("example2@example.com", VolunteerRole.AUTH_VOLUNTEER, SocialType.NAVER, "12A");

        volunteerRepository.save(volunteer1);
        volunteerRepository.save(volunteer2);

        int numberOfThreads = 2;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        VolunteerApplyRequest requestDto1 = new VolunteerApplyRequest("하노정",
                "01022223333",
                "자동차",
                "이동봉사 신청하겠습니다!");
        VolunteerApplyRequest requestDto2 = new VolunteerApplyRequest("민경혁",
                "01044445555",
                "자동차",
                "이동봉사 신청하겠습니다!");

        service.execute(() -> {
            try {
                applicationService.volunteerApply("example1@example.com", 1L, requestDto1);
            } catch (BadRequestException e) {
                System.out.println("e.getMessage() = " + e.getMessage());
            }
            latch.countDown();
        });
        service.execute(() -> {
            try {
                applicationService.volunteerApply("example2@example.com", 1L, requestDto2);
            } catch (BadRequestException e) {
                System.out.println("e.getMessage() = " + e.getMessage());
            }
            latch.countDown();
        });
        latch.await();
        Thread.sleep(1000);

        // 결과 확인
        Long count = applicationRepository.countAllByPostId(1L);
        Assertions.assertThat(count).isEqualTo(1);
    }

}