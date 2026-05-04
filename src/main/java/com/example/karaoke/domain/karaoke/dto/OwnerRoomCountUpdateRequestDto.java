package com.example.karaoke.domain.karaoke.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OwnerRoomCountUpdateRequestDto {
    private int totalRooms;
    private int availableRooms;

}
