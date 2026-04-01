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

    public static KaraokeApiResponseDto from(KaraokeApiResponse.Item item) {
        return new KaraokeApiResponseDto(
                item.getMNG_NO(),
                item.getBPLC_NM(),
                item.getROAD_NM_ADDR(),
                convertY(item.getCRD_INFO_Y()),
                convertX(item.getCRD_INFO_X()),
                parseRoomCount(item.getKAROOM_RM_CNT())
        );
    }

    private static double convertX(String x) {
        if (x == null || x.isBlank()) {
            return 0.0;
        }
        return Double.parseDouble(x);
    }

    private static double convertY(String y) {
        if (y == null || y.isBlank()) {
            return 0.0;
        }
        return Double.parseDouble(y);
    }

    private static int parseRoomCount(String value) {
        if (value == null || value.isBlank()) {
            return 0;
        }
        return Integer.parseInt(value);
    }


}

