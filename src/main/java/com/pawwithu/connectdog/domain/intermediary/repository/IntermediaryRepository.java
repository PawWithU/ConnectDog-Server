package com.pawwithu.connectdog.domain.intermediary.repository;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntermediaryRepository extends JpaRepository<Intermediary, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByName(String name);
    Optional<Intermediary> findByEmail(String email);
}
