package com.example.karaoke.domain.karaoke.service;

import com.example.karaoke.domain.karaoke.dto.OwnerKaraokeResponseDto;
import com.example.karaoke.domain.karaoke.dto.OwnerRoomCountUpdateRequestDto;
import com.example.karaoke.domain.karaoke.entity.Karaoke;
import com.example.karaoke.domain.karaoke.repository.KaraokeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerKaraokeService {

    private final KaraokeRepository karaokeRepository;

    /**
     * Owner 전용 Service
     */
    public OwnerKaraokeResponseDto getMyKaraoke(Long ownerId) {
        Karaoke karaoke = karaokeRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("등록된 노래방이 없습니다."));

        return OwnerKaraokeResponseDto.from(karaoke);
    }

    @Transactional
    public OwnerKaraokeResponseDto increaseAvailableRooms(Long ownerId) {
        Karaoke karaoke = getOwnedKaraoke(ownerId);
        karaoke.increaseAvailableRooms();
        return OwnerKaraokeResponseDto.from(karaoke);
    }

    @Transactional
    public OwnerKaraokeResponseDto decreaseAvailableRooms(Long ownerId) {
        Karaoke karaoke = getOwnedKaraoke(ownerId);
        karaoke.decreaseAvailableRooms();
        return OwnerKaraokeResponseDto.from(karaoke);
    }

    @Transactional
    public OwnerKaraokeResponseDto updateRoomCount(Long ownerId, OwnerRoomCountUpdateRequestDto requestDto) {
        Karaoke karaoke = getOwnedKaraoke(ownerId);
        karaoke.updateRoomCount(requestDto.getTotalRooms(), requestDto.getAvailableRooms());
        return OwnerKaraokeResponseDto.from(karaoke);
    }

    private Karaoke getOwnedKaraoke(Long ownerId) {
        return karaokeRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("사장님에게 연결된 노래방이 없습니다."));
    }




}
