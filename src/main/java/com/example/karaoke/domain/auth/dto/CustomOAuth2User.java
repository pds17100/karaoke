package com.example.karaoke.domain.auth.dto;

import com.example.karaoke.domain.member.entity.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User, Serializable {

    private static final long serialVersionUID = 1L; // ← 추가

    private final Long memberId;
    private final String oauthId;
    private final String nickName;
    private final MemberRole role;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(Long memberId, String oauthId, String nickName,
                            MemberRole role, Map<String, Object> attributes) {
        this.memberId = memberId;
        this.oauthId = oauthId;
        this.nickName = nickName;
        this.role = role;
        this.attributes = attributes;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getName() {
        return String.valueOf(memberId);
    }



}
