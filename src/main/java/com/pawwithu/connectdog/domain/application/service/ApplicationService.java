package com.pawwithu.connectdog.domain.application.service;

import com.pawwithu.connectdog.domain.application.dto.request.VolunteerApplyRequest;
import com.pawwithu.connectdog.domain.application.dto.response.ApplicationGetOneResponse;
import com.pawwithu.connectdog.domain.application.dto.response.ApplicationProgressingResponse;
import com.pawwithu.connectdog.domain.application.dto.response.ApplicationWaitingResponse;
import com.pawwithu.connectdog.domain.application.entity.Application;
import com.pawwithu.connectdog.domain.application.repository.ApplicationRepository;
import com.pawwithu.connectdog.domain.application.repository.CustomApplicationRepository;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import com.pawwithu.connectdog.domain.post.repository.PostRepository;
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
@Transactional
@RequiredArgsConstructor
public class ApplicationService {

    private final VolunteerRepository volunteerRepository;
    private final PostRepository postRepository;
    private final ApplicationRepository applicationRepository;
    private final CustomApplicationRepository customApplicationRepository;

    public void volunteerApply(String email, Long postId, VolunteerApplyRequest request) {
        // 이동봉사자
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        // 공고
        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException(POST_NOT_FOUND));
        // 이동봉사 중개
        Intermediary intermediary = post.getIntermediary();

        // 해당 공고에 대한 신청이 이미 존재할 경우
        if (applicationRepository.existsByPostId(postId)) {
            throw new BadRequestException(ALREADY_EXIST_APPLICATION);
        }

        // 공고 신청 저장
        Application application = request.toEntity(post, intermediary, volunteer);
        applicationRepository.save(application);

        // 공고 상태 승인 대기 중으로 변경
        post.updateStatus(PostStatus.WAITING);
    }

    @Transactional(readOnly = true)
    public List<ApplicationWaitingResponse> getWaitingApplications(String email, Pageable pageable) {
        // 이동봉사자
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        List<ApplicationWaitingResponse> waitingApplications = customApplicationRepository.getWaitingApplications(volunteer.getId(), pageable);
        return waitingApplications;
    }

    @Transactional(readOnly = true)
    public List<ApplicationProgressingResponse> getProgressingApplications(String email, Pageable pageable) {
        // 이동봉사자
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        List<ApplicationProgressingResponse> progressingApplications = customApplicationRepository.getProgressingApplications(volunteer.getId(), pageable);
        return progressingApplications;
    }

    @Transactional(readOnly = true)
    public ApplicationGetOneResponse getOneApplication(Long applicationId) {
        // 신청 내역
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new BadRequestException(APPLICATION_NOT_FOUND));
        ApplicationGetOneResponse oneApplication = ApplicationGetOneResponse.from(application);
        return oneApplication;
    }

    public void deleteApplication(Long applicationId) {
        // 신청 내역 + post
        Application application = customApplicationRepository.findByIdWithPost(applicationId).orElseThrow(() -> new BadRequestException(APPLICATION_NOT_FOUND));
        applicationRepository.delete(application);
        // 신청 취소 시: 공고 승인 대기중 -> 모집중
        Post post = application.getPost();
        post.updateStatus(PostStatus.RECRUITING);
    }
}
