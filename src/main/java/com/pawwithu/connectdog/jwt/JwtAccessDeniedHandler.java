package com.pawwithu.connectdog.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 필요한 권한이 존재하지 않는 경우에 403 Forbidden 에러를 리턴
 * -> Spring Security가 AccessDeniedHandler 인터페이스의 handle 메서드 호출
 */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        // 필요한 권한이 없이 접근하려 할때 403
        log.info("허가 받지 않은 사용자의 접근입니다.");
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}