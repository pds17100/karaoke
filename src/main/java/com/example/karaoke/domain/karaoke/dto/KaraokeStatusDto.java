package com.example.karaoke.domain.karaoke.dto;

import com.example.karaoke.domain.karaoke.entity.Karaoke;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KaraokeStatusDto implements Serializable {
    private Long id;
    private int availableRooms;

    public static KaraokeStatusDto from(Karaoke karaoke) {
        return new KaraokeStatusDto(
                karaoke.getId(),
                karaoke.getAvailableRooms()
        );
    }


}
