package com.pawwithu.connectdog.domain.post.repository;

import com.pawwithu.connectdog.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
