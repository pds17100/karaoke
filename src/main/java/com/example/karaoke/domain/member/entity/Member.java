package com.example.karaoke.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String oauthId;

    @Column(nullable = false, length = 50)
    private String nickName;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OAuthProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberRole role;

    public Member(String oauthId, String nickName, String email, OAuthProvider provider, MemberRole role) {
        this.oauthId = oauthId;
        this.nickName = nickName;
        this.email = email;
        this.provider = provider;
        this.role = role;
    }

    public static Member createKakaoMember(String oauthId, String nickName, String email) {
        return new Member(
                oauthId,
                nickName,
                email,
                OAuthProvider.KAKAO,
                MemberRole.USER
        );

    }

    public void updateProfile(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }

    public void changeRole(MemberRole role) {
        this.role = role;
    }

}
