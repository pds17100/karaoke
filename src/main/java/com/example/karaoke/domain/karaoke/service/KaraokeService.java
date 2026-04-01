package com.example.karaoke.domain.karaoke.service;

import com.example.karaoke.domain.karaoke.dto.KaraokeMapResponseDto;
import com.example.karaoke.domain.karaoke.dto.KaraokeResponseDto;
import com.example.karaoke.domain.karaoke.entity.Karaoke;
import com.example.karaoke.domain.karaoke.repository.KaraokeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KaraokeService {

    private final KaraokeRepository karaokeRepository;

    /**
     *  지도용
     */
    public List<KaraokeMapResponseDto> getKaraokesForMap() {
        return karaokeRepository.findAll()
                .stream()
                .map(KaraokeMapResponseDto::from)
                .toList();
    }


    /**
     *  상세조회용
     */
    public List<KaraokeResponseDto> getAllKaraokes() {
        return karaokeRepository.findAll().stream()
                .map(KaraokeResponseDto::from)
                .toList();
    }

    /**
     *  단건용
     */
    public KaraokeResponseDto getKaraokeById(Long karaokeId) {
        Karaoke karaoke = karaokeRepository.findById(karaokeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 노래방이 존재하지 않습니다. id=" + karaokeId));

        return KaraokeResponseDto.from(karaoke);
    }





}
