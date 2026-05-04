package com.example.karaoke.domain.ownerapplication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OwnerApplicationCreateRequestDto {
    private Long karaokeId;
    private String name;
    private String email;
    private String phone;
    private String businessNumber;

}
