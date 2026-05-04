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

    /**
     *  구미
     */
    @PostMapping("/load/gumi")
    public ResponseEntity<String> loadKaraokes() {
        karaokeSyncService.syncKaraokes("5080000");
        return ResponseEntity.ok("구미 노래방 데이터 동기화 완료");
    }

    /**
     *  서초
     */
    @PostMapping("/load/seocho")
    public ResponseEntity<String> loadKaraokes2() {
        karaokeSyncService.syncKaraokes("3210000");
        return ResponseEntity.ok("서초 노래방 데이터 동기화 완료");
    }


}
