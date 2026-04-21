package com.example.karaoke.domain.member.service;

import com.example.karaoke.domain.member.entity.Member;
import com.example.karaoke.domain.member.entity.OAuthProvider;
import com.example.karaoke.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 카카오 회원 저장, 회원 있으면 변경
     */
    @Transactional
    public Member saveOrUpdateKakaoMember(String oauthId, String nickName) {
        return memberRepository.findByOauthIdAndProvider(oauthId, OAuthProvider.KAKAO)
                .map(member -> {
                    member.updateProfile(nickName);
                    return member;
                })
                .orElseGet(() -> memberRepository.save(
                        Member.createKakaoMember(oauthId, nickName)
                ));
    }





}
