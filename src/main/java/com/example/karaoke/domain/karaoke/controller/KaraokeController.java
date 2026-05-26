package com.example.karaoke.domain.karaoke.controller;

import com.example.karaoke.domain.karaoke.dto.KaraokeMapResponseDto;
import com.example.karaoke.domain.karaoke.dto.KaraokeResponseDto;
import com.example.karaoke.domain.karaoke.dto.KaraokeStatusDto;
import com.example.karaoke.domain.karaoke.service.KaraokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/karaokes")
public class KaraokeController {

    private final KaraokeService karaokeService;

    /**
     *  지도용 (새로고침용, 캐시)
     */
    @GetMapping("/map")
    public List<KaraokeMapResponseDto> getKaraokesForMap() {
        return karaokeService.getKaraokesForMap();
    }

    /**
     *  남은 좌석 폴링용 (캐시)
     */
    @GetMapping("/status")
    public List<KaraokeStatusDto> getAllStatus() {
        return karaokeService.getAllStatus();
    }


    /**
     *  단건용
     */
    @GetMapping("/{karaokeId}")
    public KaraokeResponseDto getKaraokeById(@PathVariable Long karaokeId) {
        return karaokeService.getKaraokeById(karaokeId);
    }




}
