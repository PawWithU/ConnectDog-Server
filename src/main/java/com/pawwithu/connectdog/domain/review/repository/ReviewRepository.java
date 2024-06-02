package com.pawwithu.connectdog.domain.review.repository;

import com.pawwithu.connectdog.domain.review.entity.Review;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVolunteer(Volunteer volunteer);

}