package com.pawwithu.connectdog.domain.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import com.pawwithu.connectdog.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;

import static com.pawwithu.connectdog.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final VolunteerRepository volunteerRepository;
    private final IntermediaryRepository intermediaryRepository;

    @Value("${jwt.access.expiration}")
    private Integer accessTokenExpirationPeriod;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String email = extractUsername(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().toString(); // ROLE_INTERMEDIARY, ROLE_AUTH_INTERMEDIARY / ROLE_VOLUNTEER, ROLE_AUTH_VOLUNTEER
        String roleName = role.substring(6, role.length()-1); // INTERMEDIARY, AUTH_INTERMEDIARY / VOLUNTEER, AUTH_VOLUNTEER

        Long id;
        String accessToken;
        String refreshToken;
        if ("INTERMEDIARY".equals(roleName) || "AUTH_INTERMEDIARY".equals(roleName)) {
            id = intermediaryRepository.findByEmail(email)
                    .map(Intermediary::getId)
                    .orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));

            accessToken = jwtService.createIntermediaryAccessToken(id, roleName);
            refreshToken = jwtService.createIntermediaryRefreshToken(id, roleName);
        } else if ("VOLUNTEER".equals(roleName) || "AUTH_VOLUNTEER".equals(roleName)) {
            id = volunteerRepository.findByEmail(email)
                    .map(Volunteer::getId)
                    .orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));

            accessToken = jwtService.createVolunteerAccessToken(id, roleName);
            refreshToken = jwtService.createVolunteerRefreshToken(id, roleName);
        } else {
            throw new BadRequestException(INVALID_ROLE_NAME); // 다른 roleName 들어왔을 경우의 예외 처리
        }

        // JWT 서비스에서 토큰 정보 가져오기
        Map<String, String> tokenData = jwtService.sendAccessAndRefreshToken(roleName, accessToken, refreshToken);

        // HTTP 응답 설정
        response.setStatus(HttpServletResponse.SC_OK); // 상태 코드를 200 (OK)로 설정
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokenData));

        jwtService.updateRefreshToken(roleName, id, refreshToken);

        log.info("로그인에 성공하였습니다. 이메일 : {}", email);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpirationPeriod);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
