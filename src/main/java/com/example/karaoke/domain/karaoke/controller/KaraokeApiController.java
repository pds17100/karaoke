package com.example.karaoke.domain.karaoke.controller;

import com.example.karaoke.domain.karaoke.service.KaraokeSyncService;
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
    private final KaraokeSyncService karaokeSyncService;

    @PostMapping("/load")
    public ResponseEntity<String> loadKaraokes() {
        karaokeSyncService.syncKaraokes();
        return ResponseEntity.ok("노래방 데이터 동기화 완료");
    }


}
