package com.pawwithu.connectdog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawwithu.connectdog.domain.auth.filter.CustomIntermediaryAuthFilter;
import com.pawwithu.connectdog.domain.auth.filter.CustomVolunteerAuthFilter;
import com.pawwithu.connectdog.domain.auth.handler.LoginFailureHandler;
import com.pawwithu.connectdog.domain.auth.handler.LoginSuccessHandler;
import com.pawwithu.connectdog.domain.auth.service.IntermediaryLoginService;
import com.pawwithu.connectdog.domain.auth.service.VolunteerLoginService;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.jwt.JwtAccessDeniedHandler;
import com.pawwithu.connectdog.jwt.JwtAuthenticationEntryPoint;
import com.pawwithu.connectdog.jwt.filter.JwtAuthenticationProcessingFilter;
import com.pawwithu.connectdog.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ObjectMapper objectMapper;
    private final VolunteerLoginService volunteerLoginService;
    private final IntermediaryLoginService intermediaryLoginService;
    private final JwtService jwtService;
    private final VolunteerRepository volunteerRepository;
    private final IntermediaryRepository intermediaryRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->
                        request.requestMatchers(mvcMatcherBuilder.pattern("/volunteers/login")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/intermediaries/login")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/volunteers/login/social")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/volunteers/sign-up")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/volunteers/sign-up/email")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/intermediaries/sign-up/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/reissue-token")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/h2-console/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/css/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/js/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/images/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/error")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/favicon.ico")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/swagger-resources/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/v3/api-docs/**")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/volunteers/nickname/isDuplicated")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/fcm-test")).permitAll()
                                .requestMatchers(mvcMatcherBuilder.pattern("/volunteers/posts/{postId}/applications")).hasRole("AUTH_VOLUNTEER")
                                .requestMatchers(mvcMatcherBuilder.pattern("/intermediaries/**")).hasRole("AUTH_INTERMEDIARY")
                                .anyRequest().authenticated())
                .addFilterAfter(customVolunteerAuthFilter(), LogoutFilter.class)
                .addFilterAfter(customIntermediaryAuthFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), CustomVolunteerAuthFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), CustomIntermediaryAuthFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, volunteerRepository, intermediaryRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    @Primary
    public AuthenticationManager volunteerAuthenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(volunteerLoginService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(Collections.singletonList(provider));
    }

    @Bean
    public AuthenticationManager intermediaryAuthenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(intermediaryLoginService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(Collections.singletonList(provider));
    }

    @Bean
    public CustomVolunteerAuthFilter customVolunteerAuthFilter() {
        CustomVolunteerAuthFilter customVolunteerAuthFilter = new CustomVolunteerAuthFilter(objectMapper);
        customVolunteerAuthFilter.setAuthenticationManager(volunteerAuthenticationManager());
        customVolunteerAuthFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customVolunteerAuthFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customVolunteerAuthFilter;
    }

    @Bean
    public CustomIntermediaryAuthFilter customIntermediaryAuthFilter() {
        CustomIntermediaryAuthFilter customIntermediaryAuthFilter = new CustomIntermediaryAuthFilter(objectMapper);
        customIntermediaryAuthFilter.setAuthenticationManager(intermediaryAuthenticationManager());
        customIntermediaryAuthFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customIntermediaryAuthFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customIntermediaryAuthFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter = new JwtAuthenticationProcessingFilter(jwtService);
        return jwtAuthenticationProcessingFilter;
    }

}
