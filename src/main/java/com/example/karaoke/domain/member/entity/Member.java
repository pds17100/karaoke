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
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String oauthId;

    @Column(nullable = false, length = 50)
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OAuthProvider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberRole role;

    public Member(String oauthId, String nickName, OAuthProvider provider, MemberRole role) {
        this.oauthId = oauthId;
        this.nickName = nickName;
        this.provider = provider;
        this.role = role;
    }

    /**
     *  ADMIN 계정 생성 메서드
     */
    public static Member createAdmin(String oauthId, String nickName) {
//        return new Member(oauthId, nickName, email, OAuthProvider.KAKAO, MemberRole.ADMIN);
        return new Member(oauthId, nickName, OAuthProvider.KAKAO, MemberRole.ADMIN);
    }


    public static Member createKakaoMember(String oauthId, String nickName) {
        return new Member(
                oauthId,
                nickName,
                OAuthProvider.KAKAO,
                MemberRole.USER
        );

    }


    public void updateProfile(String nickName) {
        this.nickName = nickName;
    }

    public void changeRole(MemberRole role) {
        this.role = role;
    }

}
