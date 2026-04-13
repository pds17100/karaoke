package com.example.karaoke.domain.karaoke.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoTranscoordResponse {
    private List<Document> documents;

    @Getter
    @NoArgsConstructor
    public static class Document {
        private double x; // longitude
        private double y; // latitude
    }


}
