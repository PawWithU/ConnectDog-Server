package com.pawwithu.connectdog.domain.post.repository;

import com.pawwithu.connectdog.domain.intermediary.entity.Intermediary;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.entity.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndIntermediaryId(Long id, Long intermediaryId);
    Optional<Post> findByIdAndStatus(Long id, PostStatus postStatus);

    List<Post> findByIntermediary(Intermediary intermediary);
}
