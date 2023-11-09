package com.pawwithu.connectdog.domain.dog.repository;

import com.pawwithu.connectdog.domain.dog.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
