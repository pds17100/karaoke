package com.example.karaoke.domain.karaoke.dto;

import com.example.karaoke.domain.karaoke.entity.Karaoke;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.io.Serializable;

/**
 *  지도용
 */
@Getter
@AllArgsConstructor
public class KaraokeMapResponseDto implements Serializable {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private int totalRooms;

    public static KaraokeMapResponseDto from(Karaoke karaoke) {
        return new KaraokeMapResponseDto(
                karaoke.getId(),
                karaoke.getName(),
                karaoke.getLatitude(),
                karaoke.getLongitude(),
                karaoke.getTotalRooms()
        );
    }

}
