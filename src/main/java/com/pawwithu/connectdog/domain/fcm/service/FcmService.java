package com.pawwithu.connectdog.domain.fcm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.pawwithu.connectdog.domain.fcm.dto.request.FcmMessage;
import com.pawwithu.connectdog.domain.fcm.dto.request.IntermediaryFcmRequest;
import com.pawwithu.connectdog.domain.fcm.dto.request.VolunteerFcmRequest;
import com.pawwithu.connectdog.domain.fcm.entity.IntermediaryFcm;
import com.pawwithu.connectdog.domain.fcm.entity.VolunteerFcm;
import com.pawwithu.connectdog.domain.fcm.repository.IntermediaryFcmRepository;
import com.pawwithu.connectdog.domain.fcm.repository.VolunteerFcmRepository;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;

import static com.pawwithu.connectdog.error.ErrorCode.INTERMEDIARY_NOT_FOUND;
import static com.pawwithu.connectdog.error.ErrorCode.VOLUNTEER_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FcmService {

    @Value("${fcm.config.path}")
    private String FIREBASE_CONFIG_PATH;

    @Value("${fcm.api.url}")
    private String FIREBASE_API_URL;

    private final ObjectMapper objectMapper;
    private final VolunteerRepository volunteerRepository;
    private final VolunteerFcmRepository volunteerFcmRepository;
    private final IntermediaryRepository intermediaryRepository;
    private final IntermediaryFcmRepository intermediaryFcmRepository;

    private String getAccessToken() throws IOException {

        // firebase로 부터 access token을 가져온다.
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();

    }

    /**
     * makeMessage : 알림 파라미터들을 FCM이 요구하는 body 형태로 가공한다.
     * @param targetToken : firebase token
     * @param title : 알림 제목
     * @param body : 알림 내용
     * @return
     * */
    public String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {

        FcmMessage fcmMessage = FcmMessage.builder()
                .message(
                        FcmMessage.Message.builder()
                                .token(targetToken)
                                .notification(
                                        FcmMessage.Notification.builder()
                                                .title(title)
                                                .body(body)
                                                .build())
                                .build()
                )
                .validateOnly(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);

    }

    /**
     * 알림 푸쉬를 보내는 역할을 하는 메서드
     * @param targetToken : 푸쉬 알림을 받을 클라이언트 앱의 식별 토큰
     * */
    public void sendMessageTo(String targetToken, String title, String body) throws IOException {

        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(FIREBASE_API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer "+getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();
        log.info(response.body().string());
        return;
    }

    public void saveVolunteerFcm(String email, VolunteerFcmRequest request) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        VolunteerFcm volunteerFcm = VolunteerFcmRequest.volunteerToEntity(volunteer, request);
        volunteerFcmRepository.save(volunteerFcm);
    }

    public void saveIntermediaryFcm(String email, IntermediaryFcmRequest request) {
        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));
        IntermediaryFcm intermediaryFcm = IntermediaryFcmRequest.IntermediaryToEntity(intermediary, request);
        intermediaryFcmRepository.save(intermediaryFcm);
    }
}