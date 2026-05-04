package com.example.karaoke.domain.ownerapplication.dto;

import com.example.karaoke.domain.ownerapplication.entity.ApplicationStatus;
import com.example.karaoke.domain.ownerapplication.entity.OwnerApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerApplicationAdminListResponseDto {
    private Long applicationId;
    private String applicantName;
    private String phone;
    private String karaokeName;
    private ApplicationStatus status;


    public static OwnerApplicationAdminListResponseDto from(OwnerApplication ownerApplication) {
        return new OwnerApplicationAdminListResponseDto(
                ownerApplication.getId(),
                ownerApplication.getName(),
                ownerApplication.getPhone(),
                ownerApplication.getKaraoke().getName(),
                ownerApplication.getStatus()
        );
    }


}
