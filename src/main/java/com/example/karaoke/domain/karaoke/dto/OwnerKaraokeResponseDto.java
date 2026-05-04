package com.example.karaoke.domain.karaoke.dto;

import com.example.karaoke.domain.karaoke.entity.Karaoke;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerKaraokeResponseDto {
    private Long karaokeId;
    private String name;
    private String address;
    private int totalRooms;
    private int availableRooms;

    public static OwnerKaraokeResponseDto from(Karaoke karaoke) {
        return new OwnerKaraokeResponseDto(
                karaoke.getId(),
                karaoke.getName(),
                karaoke.getAddress(),
                karaoke.getTotalRooms(),
                karaoke.getAvailableRooms()
        );
    }

}
