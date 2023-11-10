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

import static com.pawwithu.connectdog.error.ErrorCode.POST_NOT_FOUND;
import static com.pawwithu.connectdog.error.ErrorCode.VOLUNTEER_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final VolunteerRepository volunteerRepository;
    private final PostRepository postRepository;

    public void clickBookmark(String email, Long postId) {
        Volunteer volunteer = volunteerRepository.findByEmail(email).orElseThrow(() -> new BadRequestException(VOLUNTEER_NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException(POST_NOT_FOUND));

        if (bookmarkStatus(volunteer, post)) {
            deleteBookmark(volunteer, post);
        } else {
            createBookmark(volunteer, post);
        }
    }

    public Boolean bookmarkStatus(Volunteer volunteer, Post post) {
        return bookmarkRepository.existsByVolunteerAndPost(volunteer, post);
    }

    public void deleteBookmark(Volunteer volunteer, Post post) {
        bookmarkRepository.deleteByVolunteerAndPost(volunteer, post);
    }

    public void createBookmark(Volunteer volunteer, Post post) {
        Bookmark bookmark = Bookmark.builder()
                .post(post)
                .volunteer(volunteer)
                .build();

        bookmarkRepository.save(bookmark);
    }
}
