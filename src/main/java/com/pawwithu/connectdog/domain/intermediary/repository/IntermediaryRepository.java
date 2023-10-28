package com.pawwithu.connectdog.domain.intermediary.repository;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntermediaryRepository extends JpaRepository<Intermediary, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByName(String name);
}
