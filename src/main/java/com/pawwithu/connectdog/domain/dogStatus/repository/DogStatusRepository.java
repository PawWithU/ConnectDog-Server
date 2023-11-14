package com.pawwithu.connectdog.domain.dogStatus.repository;

import com.pawwithu.connectdog.domain.dogStatus.entity.DogStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogStatusRepository extends JpaRepository<DogStatus, Long> {
}
