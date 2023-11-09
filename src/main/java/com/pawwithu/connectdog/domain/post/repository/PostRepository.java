package com.pawwithu.connectdog.domain.post.repository;

import com.pawwithu.connectdog.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
