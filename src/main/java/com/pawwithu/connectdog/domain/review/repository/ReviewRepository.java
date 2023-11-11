package com.pawwithu.connectdog.domain.review.repository;

import com.pawwithu.connectdog.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}