package com.example.karaoke.domain.karaoke.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  상세용
 */
@Getter
@AllArgsConstructor
public class KaraokeApiResponseDto {
    private String manageNo;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private int totalRooms;


    public static int parseRoomCount(String value) {
        if (value == null || value.isBlank()) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public static double parseCoordinate(String value) {
        if (value == null || value.isBlank()) {
            return 0.0;
        }
        return Double.parseDouble(value);
    }


}

