package com.pawwithu.connectdog.jwt.filter;

import com.pawwithu.connectdog.domain.member.entity.Member;
import com.pawwithu.connectdog.domain.member.repository.MemberRepository;
import com.pawwithu.connectdog.error.exception.custom.TokenException;
import com.pawwithu.connectdog.jwt.service.JwtService;
import com.pawwithu.connectdog.jwt.util.PasswordUtil;
import com.pawwithu.connectdog.util.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.pawwithu.connectdog.error.ErrorCode.ALREADY_LOGOUT_MEMBER;
import static com.pawwithu.connectdog.error.ErrorCode.INVALID_TOKEN;

/**
 * Jwt 인증 필터
 * "/login" 이외의 URI 요청이 왔을 때 처리하는 필터
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final Set<String> NO_CHECK_URLS = new HashSet<>(Arrays.asList("/login")); // "/login"으로 들어오는 요청은 Filter 작동 X

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (NO_CHECK_URLS.stream().anyMatch(url -> url.equals(request.getRequestURI()))) {
            filterChain.doFilter(request, response); // NO_CHECK_URLS 요청이 들어오면, 다음 필터 호출
            return;
        }
        log.info("uri = {}, query = {}", request.getRequestURI(), request.getQueryString());
        log.info("JwtAuthenticationProcessingFilter 호출");

        String accessToken = jwtService.extractAccessToken(request).orElse(null);
        if (jwtService.isTokenValid(accessToken)) {
            if(redisUtil.hasKeyBlackList(accessToken)) {
                throw new TokenException(ALREADY_LOGOUT_MEMBER);
            }
            getAuthentication(accessToken);
            filterChain.doFilter(request, response);
            return;
        }

        // Access Token이 만료되어 Refresh Token으로 Access Token 재발급
        String refreshToken = jwtService.extractRefreshToken(request).orElse(null);
        if (StringUtils.hasText(accessToken) && jwtService.isTokenValid(refreshToken)) {
            log.info("AccessToken 재발급");
            Long id = jwtService.extractId(refreshToken).orElseThrow(() -> new TokenException(INVALID_TOKEN));
            if (isRefreshTokenMatch(id, refreshToken)) {
                reIssueRefreshAndAccessToken(response, refreshToken, id);
            }
            filterChain.doFilter(request, response);
            return;
        }

        log.info("유효한 JWT 토큰이 없습니다. uri: {}, {}", request.getRequestURI(), accessToken);
        filterChain.doFilter(request, response);
    }

    /**
     * AccessToken, RefreshToken 재발급 + 인증 + 응답 헤더에 보내기
     */
    private void reIssueRefreshAndAccessToken(HttpServletResponse response, String refreshToken, Long id) {
        String newAccessToken = jwtService.createAccessToken(id);
        String newRefreshToken = jwtService.createRefreshToken(id);
        getAuthentication(newAccessToken);
        redisUtil.delete(id);
        jwtService.updateRefreshToken(id, newRefreshToken);
        jwtService.sendAccessAndRefreshToken(response, newAccessToken, refreshToken);
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

    /**
     * [인증 처리 메소드]
     * 인증 허가 처리된 객체를 SecurityContextHolder에 담기
     */
    public void getAuthentication(String accessToken) {
        log.info("인증 처리 메소드 getAuthentication() 호출");
        jwtService.extractId(accessToken)
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
