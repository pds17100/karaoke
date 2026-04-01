package com.example.karaoke.domain.member.repository;

import com.example.karaoke.domain.member.entity.Member;
import com.example.karaoke.domain.member.entity.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthIdAndProvider(String oauthId, OAuthProvider provider);

}
