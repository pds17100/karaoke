package com.example.karaoke.domain.karaoke.dto;

import com.example.karaoke.domain.karaoke.entity.Karaoke;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KaraokeResponseDto {
    private Long id;
    private String manageNo;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private int totalRooms;
    private int availableRooms;
    private boolean owned;

    public static KaraokeResponseDto from(Karaoke karaoke) {
        return new KaraokeResponseDto(
                karaoke.getId(),
                karaoke.getManageNo(),
                karaoke.getName(),
                karaoke.getAddress(),
                karaoke.getLatitude(),
                karaoke.getLongitude(),
                karaoke.getTotalRooms(),
                karaoke.getAvailableRooms(),
                karaoke.getOwner() != null
        );
    }

}
