package com.pawwithu.connectdog.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pawwithu.connectdog.domain.member.entity.Member;
import com.pawwithu.connectdog.domain.member.repository.MemberRepository;
import com.pawwithu.connectdog.error.exception.custom.TokenException;
import com.pawwithu.connectdog.jwt.util.PasswordUtil;
import com.pawwithu.connectdog.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.pawwithu.connectdog.error.ErrorCode.ALREADY_LOGOUT_MEMBER;
import static com.pawwithu.connectdog.error.ErrorCode.INVALID_TOKEN;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    // 프로퍼티 주입 부분
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Integer accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Integer refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String ID_CLAIM = "id";
    private static final String BEARER = "Bearer ";
    private final MemberRepository memberRepository;
//    private final RedisTemplate<String, String> redisTemplate; // 빈 주입 충돌 -> 명시적으로 주입하면 되지만 여기선 쓰이지 않으므로 주석
    private final RedisUtil redisUtil;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


    // AccessToken & RefreshToken 생성 메소드 부분
    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(Long id) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰 만료 시간 설정
                .withClaim(ID_CLAIM, id)
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * RefreshToken 생성 메소드
     */
    public String createRefreshToken(Long id) {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .withClaim(ID_CLAIM, id)
                .sign(Algorithm.HMAC512(secretKey));
    }

    // AccessToken & RefreshToken Response Header 추가 메소드 부분
    /**
     * AccessToken 헤더에 실어서 보내기
     */
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        log.info("sendAccessToken 메소드 실행");
    }

    /**
     * 로그인 시 AccessToken + RefreshToken 헤더에 실어서 보내기
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    /**
     * AccessToken 헤더 설정
     */
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, "Bearer " + accessToken);
    }

    /**
     * RefreshToken 헤더 설정
     */
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, "Bearer " + refreshToken);
    }

    // 클라이언트의 요청에서 JWT Token, id 추출하는 부분
    // Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서, 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
    /**
     * 헤더에서 RefreshToken 추출
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * 헤더에서 AccessToken 추출
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * AccessToken에서 Id 추출 -> 유효하지 않다면 빈 Optional 객체 반환
     */
    public Optional<Long> extractId(String token) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(token)
                    .getClaim(ID_CLAIM)
                    .asLong());
        } catch (Exception e) {
            log.error("토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    /**
     * RefreshToken 저장(업데이트)
     */
    public void updateRefreshToken(Long id, String refreshToken) {
        redisUtil.set(id, refreshToken, refreshTokenExpirationPeriod);
    }

    /**
     * 토큰 유효성 검사
     */
    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);

            if(redisUtil.hasKeyBlackList(token)) {
                throw new TokenException(ALREADY_LOGOUT_MEMBER);
            }
            return true;
        } catch (Exception e) {
            log.info("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }


    // 토큰 재발급 시 사용할 메소드
    /**
     * AccessToken, RefreshToken 재발급 + 인증 + 응답 헤더에 보내기
     */
    private void reIssueRefreshAndAccessToken(HttpServletResponse response, String refreshToken, Long id) {
        String newAccessToken = createAccessToken(id);
        String newRefreshToken = createRefreshToken(id);
        getAuthentication(newAccessToken);
        redisUtil.delete(id);
        updateRefreshToken(id, newRefreshToken);
        sendAccessAndRefreshToken(response, newAccessToken, refreshToken);
        log.info("AccessToken, RefreshToken 재발급 완료");
    }

    /**
     * RefreshToken 검증 메소드
     */
    public boolean isRefreshTokenMatch(Long id, String refreshToken) {
        log.info("RefreshToken 검증");
        if (redisUtil.get(id).equals(refreshToken)) {
            return true;
        }
        throw new TokenException(INVALID_TOKEN);
    }

    // 인증 처리/허가 메소드

    /**
     * [인증 처리 메소드]
     * 인증 허가 처리된 객체를 SecurityContextHolder에 담기
     */
    public void getAuthentication(String accessToken) {
        log.info("인증 처리 메소드 getAuthentication() 호출");
        extractId(accessToken)
                .ifPresent(id -> memberRepository.findById(id)
                        .ifPresent(this::saveAuthentication));
    }

    /**
     * [인증 허가 메소드]
     * 파라미터의 유저 : 우리가 만든 회원 객체 / 빌더의 유저 : UserDetails의 User 객체
     */
    public void saveAuthentication(Member member) {
        log.info("인증 허가 메소드 saveAuthentication() 호출");
        String password = member.getPassword();
        if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
            password = PasswordUtil.generateRandomPassword();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(member.getEmail())
                .password(password)
                .roles(member.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
