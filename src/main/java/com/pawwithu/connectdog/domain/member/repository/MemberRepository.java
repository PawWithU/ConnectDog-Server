package com.pawwithu.connectdog.domain.member.repository;

import com.pawwithu.connectdog.domain.member.entity.Member;
import com.pawwithu.connectdog.domain.member.entity.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);

    // 소셜 타입과 소셜의 식별값으로 회원 찾는 메소드 -> 추가 정보를 입력받아 회원가입 진행 시 이용
    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Boolean existsByEmail(String email);
    Boolean existsByNickname(String nickname);

}
