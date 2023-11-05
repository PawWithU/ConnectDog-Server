package com.pawwithu.connectdog.domain.oauth.service;

import com.pawwithu.connectdog.domain.oauth.dto.request.SocialLoginRequest;
import com.pawwithu.connectdog.domain.oauth.dto.response.OAuthInfoResponse;
import com.pawwithu.connectdog.domain.oauth.dto.response.LoginResponse;
import com.pawwithu.connectdog.domain.volunteer.entity.SocialType;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.entity.VolunteerRole;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {
    private final VolunteerRepository volunteerRepository;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final JwtService jwtService;

    public LoginResponse volunteerSocialLogin(SocialLoginRequest request) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(request);
        SocialType socialType = oAuthInfoResponse.getSocialType();
        String socialId = oAuthInfoResponse.getId();

        String roleName = "VOLUNTEER";
        Long id = findOrSaveVolunteer(socialType, socialId); // Volunteer id 반환

        String accessToken = jwtService.createAccessToken(id, roleName);
        String refreshToken = jwtService.createRefreshToken(id, roleName);
        jwtService.updateRefreshToken(roleName, id, refreshToken);

        return LoginResponse.of(roleName, accessToken, refreshToken);
    }

    private Long findOrSaveVolunteer(SocialType socialType, String socialId) {

        Optional<Volunteer> volunteer = volunteerRepository.findBySocialTypeAndSocialId(socialType, socialId);

        if (volunteer.isPresent()) {
            return volunteer.get().getId();
        } else {
            return saveVolunteer(socialType, socialId);
        }
    }

    private Long saveVolunteer(SocialType socialType, String socialId) {
        Volunteer createdVolunteer = new Volunteer("랜덤?", VolunteerRole.VOLUNTEER, socialType, socialId);

        return volunteerRepository.save(createdVolunteer).getId();
    }
}
