package com.pawwithu.connectdog.domain.auth.service;

import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.pawwithu.connectdog.error.ErrorCode.VOLUNTEER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class VolunteerLoginService implements UserDetailsService {

    private final VolunteerRepository volunteerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));

        return org.springframework.security.core.userdetails.User.builder()
                .username(volunteer.getEmail())
                .password(volunteer.getPassword())
                .roles(volunteer.getRole().name())
                .build();

    }
}