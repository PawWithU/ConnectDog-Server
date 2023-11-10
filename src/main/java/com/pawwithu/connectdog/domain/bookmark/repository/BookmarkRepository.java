package com.pawwithu.connectdog.domain.bookmark.repository;

import com.pawwithu.connectdog.domain.bookmark.entity.Bookmark;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    void deleteByVolunteerAndPost(Volunteer volunteer, Post post);

    Boolean existsByVolunteerAndPost(Volunteer volunteer, Post post);
}
