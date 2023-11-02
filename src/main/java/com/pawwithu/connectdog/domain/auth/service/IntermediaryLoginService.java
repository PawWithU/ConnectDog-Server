package com.pawwithu.connectdog.domain.auth.service;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.intermediary.repository.IntermediaryRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.pawwithu.connectdog.error.ErrorCode.INTERMEDIARY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class IntermediaryLoginService implements UserDetailsService {

    private final IntermediaryRepository intermediaryRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Intermediary intermediary = intermediaryRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(INTERMEDIARY_NOT_FOUND));

        return org.springframework.security.core.userdetails.User.builder()
                .username(intermediary.getEmail())
                .password(intermediary.getPassword())
                .roles(intermediary.getRole().name())
                .build();

    }
}