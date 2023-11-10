package com.pawwithu.connectdog.domain.bookmark.service;

import com.pawwithu.connectdog.domain.bookmark.entity.Bookmark;
import com.pawwithu.connectdog.domain.bookmark.repository.BookmarkRepository;
import com.pawwithu.connectdog.domain.post.entity.Post;
import com.pawwithu.connectdog.domain.post.repository.PostRepository;
import com.pawwithu.connectdog.domain.volunteer.entity.Volunteer;
import com.pawwithu.connectdog.domain.volunteer.repository.VolunteerRepository;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pawwithu.connectdog.error.ErrorCode.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final VolunteerRepository volunteerRepository;
    private final PostRepository postRepository;

    public void createBookmark(String email, Long postId) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException(POST_NOT_FOUND));

        if (bookmarkStatus(volunteer, post)) throw new BadRequestException(ALREADY_EXIST_BOOKMARK);

        Bookmark bookmark = Bookmark.builder()
                .post(post)
                .volunteer(volunteer)
                .build();

        bookmarkRepository.save(bookmark);
    }

    public void deleteBookmark(String email, Long postId) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException(POST_NOT_FOUND));

        if (!bookmarkStatus(volunteer, post)) throw new BadRequestException(NOT_FOUND_BOOKMARK);

        bookmarkRepository.deleteByVolunteerAndPost(volunteer, post);
    }

    public Boolean bookmarkStatus(Volunteer volunteer, Post post) {
        return bookmarkRepository.existsByVolunteerAndPost(volunteer, post);
    }

}
