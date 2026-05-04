package com.example.karaoke.domain.karaoke.dto;

import com.example.karaoke.domain.karaoke.entity.Karaoke;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  지도용
 */
@Getter
@AllArgsConstructor
public class KaraokeMapResponseDto {
    private Long id;
    private double latitude;
    private double longitude;

    public static KaraokeMapResponseDto from(Karaoke karaoke) {
        return new KaraokeMapResponseDto(
                karaoke.getId(),
                karaoke.getLatitude(),
                karaoke.getLongitude()
        );
    }

}
