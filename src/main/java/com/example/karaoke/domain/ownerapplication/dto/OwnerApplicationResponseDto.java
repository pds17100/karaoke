package com.example.karaoke.domain.ownerapplication.dto;

import com.example.karaoke.domain.ownerapplication.entity.ApplicationStatus;
import com.example.karaoke.domain.ownerapplication.entity.OwnerApplication;
import lombok.Getter;

@Getter
public class OwnerApplicationResponseDto {

    private final Long applicationId;
    private final String applicantName;
    private final String email;
    private final String phone;
    private final String businessNumber;
    private final String businessLicenseImageUrl;
    private final String karaokeName;
    private final ApplicationStatus status;
    private final String rejectReason;

    public OwnerApplicationResponseDto(OwnerApplication ownerApplication) {
        this.applicationId = ownerApplication.getId();
        this.applicantName = ownerApplication.getName();
        this.email = ownerApplication.getEmail();
        this.phone = ownerApplication.getPhone();
        this.businessNumber = ownerApplication.getBusinessNumber();
        this.businessLicenseImageUrl = ownerApplication.getBusinessLicenseImageUrl();
        this.karaokeName = ownerApplication.getKaraoke().getName();
        this.status = ownerApplication.getStatus();
        this.rejectReason = ownerApplication.getRejectReason();
    }


}
