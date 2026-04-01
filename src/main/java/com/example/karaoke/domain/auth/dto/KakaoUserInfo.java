package com.example.karaoke.domain.auth.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class KakaoUserInfo {
    private final Map<String, Object> attributes;

    public String getOauthId() {
        return String.valueOf(attributes.get(("id")));
    }

    @SuppressWarnings("unchecked")
    public String getNickName() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount == null) {
            return "카카오회원";
        }

        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        if (profile == null) {
            return "카카오회원";
        }

        Object nickname = profile.get("nickname");
        return nickname != null ? nickname.toString() : "카카오회원";
    }

    @SuppressWarnings("unchecked")
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        if (kakaoAccount == null) {
            return null;
        }

        Object email = kakaoAccount.get("email");
        return email != null ? email.toString() : null;
    }


}
