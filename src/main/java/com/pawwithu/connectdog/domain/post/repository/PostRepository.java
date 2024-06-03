package com.pawwithu.connectdog.domain.post.repository;

import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndIntermediaryId(Long id, Long intermediaryId);
    Optional<Post> findByIdAndStatus(Long id, PostStatus postStatus);
}
