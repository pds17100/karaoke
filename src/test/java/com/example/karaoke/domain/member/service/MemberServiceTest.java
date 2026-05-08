package com.example.karaoke.domain.member.service;

import com.example.karaoke.domain.member.entity.Member;
import com.example.karaoke.domain.member.entity.OAuthProvider;
import com.example.karaoke.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("신규 카카오 회원 - DB 저장")
    void saveOrUpdateKakaoMember_신규회원_저장() {
        //given
        String oauthId = "12345";
        String nickName = "홍길동";

        given(memberRepository.findByOauthIdAndProvider(oauthId, OAuthProvider.KAKAO))
                .willReturn(Optional.empty());

        Member newMember = Member.createKakaoMember(oauthId, nickName);
        given(memberRepository.save(any(Member.class)))
                .willReturn(newMember);

        //when
        Member result = memberService.saveOrUpdateKakaoMember(oauthId, nickName);

        //then
        assertThat(result.getNickName()).isEqualTo(nickName);
        verify(memberRepository).save(any(Member.class));

    }


}
