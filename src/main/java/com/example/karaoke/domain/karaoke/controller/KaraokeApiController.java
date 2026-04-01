package com.example.karaoke.domain.karaoke.controller;

import com.example.karaoke.domain.karaoke.service.KaraokeApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 공공 데이터 api 받아오기
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/karaokes")
public class KaraokeApiController {
    private final KaraokeApiService karaokeApiService;

    @PostMapping("/load")
    public ResponseEntity<String> loadKaraokes() {
        karaokeApiService.fetchAndSaveKaraokes();
        return ResponseEntity.ok("노래방 데이터 저장 완료");
    }


}
