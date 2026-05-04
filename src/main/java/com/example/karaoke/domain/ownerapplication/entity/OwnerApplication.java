package com.example.karaoke.domain.ownerapplication.entity;

import com.example.karaoke.domain.karaoke.entity.Karaoke;
import com.example.karaoke.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OwnerApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownerApplication_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String name;

    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String businessNumber;

    private String businessLicenseImageUrl;

    @Column(length = 50)
    private String rejectReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "karaoke_id")
    private Karaoke karaoke;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;


    public OwnerApplication(Member member, String name, String email, String phone, String businessNumber, String businessLicenseImageUrl, Karaoke karaoke, ApplicationStatus status) {
        this.member = member;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.businessNumber = businessNumber;
        this.businessLicenseImageUrl = businessLicenseImageUrl;
        this.karaoke = karaoke;
        this.status = status;
    }

    /**
     *  정적 생성 메서드
     */
    public static OwnerApplication create(Member member, String name, String email, String phone,
                                          String businessNumber, String businessLicenseImageUrl,
                                          Karaoke karaoke) {
        return new OwnerApplication(
                member,
                name,
                email,
                phone,
                businessNumber,
                businessLicenseImageUrl,
                karaoke,
                ApplicationStatus.PENDING
        );
    }


    public void reject(String rejectReason) {
        this.status = ApplicationStatus.REJECTED;
        this.rejectReason = rejectReason;
    }

    public void approve() {
        this.status = ApplicationStatus.APPROVED;
        this.rejectReason = null;
    }

    public boolean isPending() {
        return this.status == ApplicationStatus.PENDING;
    }



}
