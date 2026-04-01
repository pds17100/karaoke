package com.example.karaoke.domain.member.service;

import com.example.karaoke.domain.exception.BusinessException;
import com.example.karaoke.domain.exception.ErrorCode;
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
    public Member saveOrUpdateKakaoMember(String oauthId, String nickName, String email) {
        return memberRepository.findByOauthIdAndProvider(oauthId, OAuthProvider.KAKAO)
                .map(member -> {
                    member.updateProfile(nickName, email);
                    return member;
                })
                .orElseGet(() -> memberRepository.save(
                        Member.createKakaoMember(oauthId, nickName, email)
                ));
    }

    /**
     * 회원 조회
     */
    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }



}
