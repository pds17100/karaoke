package com.example.karaoke.domain.karaoke.service;

import com.example.karaoke.domain.karaoke.dto.KaraokeApiResponseDto;
import com.example.karaoke.domain.karaoke.entity.Karaoke;
import com.example.karaoke.domain.karaoke.repository.KaraokeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KaraokeSyncService {
    private final KaraokeApiService karaokeApiService;
    private final KaraokeRepository karaokeRepository;


    /**
     *  공공데이터를 DB와 동기화 (N+1 문제 해결), 구미
     */
    public void syncKaraokes(String regionCode) {
        List<KaraokeApiResponseDto> dtos = karaokeApiService.fetchKaraokes(regionCode);

        // manageNo에 지역코드 prefix 붙이기
        List<String> manageNos = dtos.stream()
                .map(dto -> regionCode + "_" + dto.getManageNo())
                .toList();

        // 2. SELECT 1번으로 기존 데이터 전부 조회 → Map으로 변환
        Map<String, Karaoke> existingMap = karaokeRepository
                .findByManageNoIn(manageNos)
                .stream()
                .collect(Collectors.toMap(Karaoke::getManageNo, k -> k));

        // 3. update vs insert 분류
        List<Karaoke> toInsert = new ArrayList<>();

        for (KaraokeApiResponseDto dto : dtos) {
            String prefixedManageNo = regionCode + "_" + dto.getManageNo();
            Karaoke existing = existingMap.get(prefixedManageNo);

            if (existing != null) {
                // 기존 엔티티: dirty checking으로 자동 UPDATE (save 불필요)
                existing.updateBasicInfo(
                        dto.getName(),
                        dto.getAddress(),
                        dto.getLatitude(),
                        dto.getLongitude(),
                        dto.getTotalRooms()
                );
            } else {
                // 신규 엔티티: INSERT 대상 목록에 추가
                toInsert.add(Karaoke.builder()
                        .manageNo(prefixedManageNo)
                        .name(dto.getName())
                        .address(dto.getAddress())
                        .latitude(dto.getLatitude())
                        .longitude(dto.getLongitude())
                        .totalRooms(dto.getTotalRooms())
                        .availableRooms(dto.getTotalRooms())
                        .owner(null)
                        .build());
            }
        }

        // 4. 신규 데이터 배치 INSERT (SELECT 1번 + INSERT 1번으로 종료)
        karaokeRepository.saveAll(toInsert);
    }



}
