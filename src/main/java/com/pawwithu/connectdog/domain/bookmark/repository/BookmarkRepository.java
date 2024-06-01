package com.pawwithu.connectdog.domain.bookmark.repository;

import com.pawwithu.connectdog.domain.bookmark.entity.Bookmark;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    void deleteByVolunteerAndPost(Volunteer volunteer, Post post);

    Boolean existsByVolunteerAndPost(Volunteer volunteer, Post post);

    Boolean existsByVolunteerIdAndPostId(Long volunteerId, Long postId);

    @Modifying
    @Query("DELETE FROM VolunteerBadge vb WHERE vb.volunteer.id = :volunteerId")
    void deleteByVolunteerId(@Param("volunteerId") Long volunteerId);

}
