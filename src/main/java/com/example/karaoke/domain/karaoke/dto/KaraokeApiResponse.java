package com.example.karaoke.domain.karaoke.dto;

import lombok.Getter;

import java.util.List;

/**
 * 공공 데이터 JSON 원본
 */
@Getter
public class KaraokeApiResponse {
    private Response response;

    @Getter
    public static class Response {
        private Body body;
    }

    @Getter
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    public static class Items {
        private List<Item> item;
    }

    @Getter
    public static class Item {
        private String MNG_NO;         // 관리번호
        private String BPLC_NM;        // 이름
        private String ROAD_NM_ADDR;  // 주소
        private String CRD_INFO_X;    // 좌표 X
        private String CRD_INFO_Y;    // 좌표 Y
        private String KAROOM_RM_CNT; // 방 개수
        private String SALS_STTS_NM;  // 영업 상태
    }

}
