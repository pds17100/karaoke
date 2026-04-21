package com.example.karaoke.domain.auth.service;

import com.example.karaoke.domain.auth.dto.CustomOAuth2User;
import com.example.karaoke.domain.auth.dto.KakaoUserInfo;
import com.example.karaoke.domain.member.entity.Member;
import com.example.karaoke.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if (!"kakao".equals(registrationId)) {
            throw new OAuth2AuthenticationException("지원하지 않는 OAuth provider 입니다.");
        }

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(attributes);

        Member member = memberService.saveOrUpdateKakaoMember(
                kakaoUserInfo.getOauthId(),
                kakaoUserInfo.getNickName()
        );


        return new CustomOAuth2User(
                member.getId(),
                member.getOauthId(),
                member.getNickName(),
                member.getRole(),
                attributes
        );

    }

}
