package com.example.karaoke.domain.karaoke.dto;

import com.example.karaoke.domain.karaoke.entity.Karaoke;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KaraokeResponseDto {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private int availableRooms;
//    private boolean owned;

    public static KaraokeResponseDto from(Karaoke karaoke) {
        return new KaraokeResponseDto(
                karaoke.getId(),
                karaoke.getName(),
                karaoke.getLatitude(),
                karaoke.getLongitude(),
                karaoke.getAvailableRooms()
//                karaoke.getOwner() != null
        );
    }

}
