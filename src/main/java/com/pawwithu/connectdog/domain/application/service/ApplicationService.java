package com.pawwithu.connectdog.domain.application.service;

import com.pawwithu.connectdog.domain.application.dto.request.VolunteerApplyRequest;
import com.pawwithu.connectdog.domain.application.dto.response.*;
import com.pawwithu.connectdog.domain.application.entity.Application;
import com.pawwithu.connectdog.domain.application.entity.ApplicationStatus;
import com.pawwithu.connectdog.domain.application.repository.ApplicationRepository;
import com.pawwithu.connectdog.domain.application.repository.CustomApplicationRepository;
import com.pawwithu.connectdog.domain.dogStatus.repository.DogStatusRepository;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import com.pawwithu.connectdog.domain.post.repository.PostRepository;
import com.pawwithu.connectdog.domain.review.repository.ReviewRepository;
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
    private final IntermediaryRepository intermediaryRepository;

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
    public List<ApplicationVolunteerWaitingResponse> getVolunteerWaitingApplications(String email, Pageable pageable) {
        // 이동봉사자
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        List<ApplicationVolunteerWaitingResponse> waitingApplications = customApplicationRepository.getVolunteerWaitingApplications(volunteer.getId(), pageable);
        return waitingApplications;
    }

    @Transactional(readOnly = true)
    public List<ApplicationVolunteerProgressingResponse> getVolunteerProgressingApplications(String email, Pageable pageable) {
        // 이동봉사자
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        List<ApplicationVolunteerProgressingResponse> progressingApplications = customApplicationRepository.getVolunteerProgressingApplications(volunteer.getId(), pageable);
        return progressingApplications;
    }

    @Transactional(readOnly = true)
    public ApplicationVolunteerGetOneResponse getVolunteerOneApplication(String email, Long applicationId) {
        // 이동봉사자
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        // 신청 내역
        Application application = applicationRepository.findByIdAndVolunteerId(applicationId, volunteer.getId()).orElseThrow(() -> new BadRequestException(APPLICATION_NOT_FOUND));
        ApplicationVolunteerGetOneResponse oneApplication = ApplicationVolunteerGetOneResponse.from(application);
        return oneApplication;
    }

    public ApplicationSuccessResponse deleteApplication(String email, Long applicationId) {
        // 이동봉사자
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        // 신청 내역 + post
        Application application = customApplicationRepository.findByIdAndVolunteerIdWithPost(applicationId, volunteer.getId()).orElseThrow(() -> new BadRequestException(APPLICATION_NOT_FOUND));
        applicationRepository.delete(application);
        // 상태 업데이트 (승인 대기중 -> 모집중)
        Post post = application.getPost();
        post.updateStatus(PostStatus.RECRUITING);
        ApplicationSuccessResponse isSuccess = ApplicationSuccessResponse.of(true);
        return isSuccess;
    }

    public ApplicationSuccessResponse confirmApplication(String email, Long applicationId) {
        // 이동봉사 중개
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        // 신청 내역 + post
        Application application = customApplicationRepository.findByIdAndIntermediaryIdAndStatusWithPost(applicationId, intermediary.getId(), ApplicationStatus.WAITING).orElseThrow(() -> new BadRequestException(APPLICATION_NOT_FOUND));
        Post post = application.getPost();
        // 상태 업데이트 (승인 대기중 -> 진행중)
        application.updateStatus(ApplicationStatus.PROGRESSING);
        post.updateStatus(PostStatus.PROGRESSING);
        ApplicationSuccessResponse isSuccess = ApplicationSuccessResponse.of(true);
        return isSuccess;
    }

    public ApplicationSuccessResponse cancelApplication(String email, Long applicationId) {
        // 이동봉사 중개
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        // 신청 내역 + post
        Application application = customApplicationRepository.findByIdAndIntermediaryIdAndStatusWithPost(applicationId, intermediary.getId(), ApplicationStatus.WAITING).orElseThrow(() -> new BadRequestException(APPLICATION_NOT_FOUND));
        applicationRepository.delete(application);
        // 상태 업데이트 (승인 대기중 -> 모집중)
        Post post = application.getPost();
        post.updateStatus(PostStatus.RECRUITING);
        ApplicationSuccessResponse isSuccess = ApplicationSuccessResponse.of(true);
        return isSuccess;
    }

    @Transactional(readOnly = true)
    public List<ApplicationIntermediaryWaitingResponse> getIntermediaryWaitingApplications(String email, Pageable pageable) {
        // 이동봉사 중개
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        List<ApplicationIntermediaryWaitingResponse> waitingApplications = customApplicationRepository.getIntermediaryWaitingApplications(intermediary.getId(), pageable);
        return waitingApplications;
    }

    @Transactional(readOnly = true)
    public List<ApplicationIntermediaryProgressingResponse> getIntermediaryProgressingApplications(String email, Pageable pageable) {
        // 이동봉사 중개
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        List<ApplicationIntermediaryProgressingResponse> progressingApplications = customApplicationRepository.getIntermediaryProgressingApplications(intermediary.getId(), pageable);
        return progressingApplications;
    }

    @Transactional(readOnly = true)
    public List<ApplicationVolunteerCompletedResponse> getVolunteerCompletedApplications(String email, Pageable pageable) {
        // 이동봉사자
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        List<ApplicationVolunteerCompletedResponse> completedApplications = customApplicationRepository.getVolunteerCompletedApplications(volunteer.getId(), pageable);
        return completedApplications;
    }

    @Transactional(readOnly = true)
    public List<ApplicationIntermediaryCompletedResponse> getIntermediaryCompletedApplications(String email, Pageable pageable) {
        // 이동봉사 중개
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        List<ApplicationIntermediaryCompletedResponse> completedApplications = customApplicationRepository.getIntermediaryCompletedApplications(intermediary.getId(), pageable);
        return completedApplications;
    }

    @Transactional(readOnly = true)
    public ApplicationIntermediaryGetOneResponse getIntermediaryOneApplication(String email, Long applicationId) {
        // 이동봉사 중개
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        // 신청 내역
        Application application = applicationRepository.findByIdAndIntermediaryId(applicationId, intermediary.getId()).orElseThrow(() -> new BadRequestException(APPLICATION_NOT_FOUND));
        ApplicationIntermediaryGetOneResponse oneApplication = ApplicationIntermediaryGetOneResponse.from(application);
        return oneApplication;
    }

    @Transactional(readOnly = true)
    public ApplicationVolunteerInfoResponse getMyInfo(String email) {
        // 이동봉사자
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        ApplicationVolunteerInfoResponse volunteerInfo = ApplicationVolunteerInfoResponse.of(volunteer.getName(), volunteer.getPhone());
        return volunteerInfo;
    }

    public ApplicationSuccessResponse completeApplication(String email, Long applicationId) {
        // 이동봉사 중개
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        // 신청 내역 + post
        Application application = customApplicationRepository.findByIdAndIntermediaryIdAndStatusWithPost(applicationId, intermediary.getId(), ApplicationStatus.PROGRESSING).orElseThrow(() -> new BadRequestException(APPLICATION_NOT_FOUND));
        Post post = application.getPost();
        // 상태 업데이트 (진행중 -> 봉사 완료)
        application.updateStatus(ApplicationStatus.COMPLETED);
        post.updateStatus(PostStatus.COMPLETED);
        ApplicationSuccessResponse isSuccess = ApplicationSuccessResponse.of(true);
        return isSuccess;
    }
}
