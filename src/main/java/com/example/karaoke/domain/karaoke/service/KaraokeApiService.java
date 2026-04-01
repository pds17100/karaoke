package com.example.karaoke.domain.karaoke.service;

import com.example.karaoke.domain.karaoke.config.KaraokeProperties;
import com.example.karaoke.domain.karaoke.dto.KaraokeApiResponse;
import com.example.karaoke.domain.karaoke.dto.KaraokeApiResponseDto;
import com.example.karaoke.domain.karaoke.entity.Karaoke;
import com.example.karaoke.domain.karaoke.repository.KaraokeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 공공 데이터 api 받아오기
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KaraokeApiService {
    private final RestTemplate restTemplate;
    private final KaraokeRepository karaokeRepository;
    private final KaraokeProperties karaokeProperties;



    /**
     * 공공데이터 전체 조회 후 DB 저장
     */
    @Transactional
    public void fetchAndSaveKaraokes() {
        int pageNo = 1;
        int numOfRows = 100;

        List<Karaoke> result = new ArrayList<>();

        while (true) {
            KaraokeApiResponse response = callApi(pageNo, numOfRows);
            List<KaraokeApiResponse.Item> items = extractItems(response);

            if (items.isEmpty()) {
                break;
            }

            List<Karaoke> karaokeList = items.stream()
                    .map(KaraokeApiResponseDto::from)
                    .filter(dto -> dto.getManageNo() != null && !dto.getManageNo().isBlank())
                    .filter(dto -> !karaokeRepository.existsByManageNo(dto.getManageNo()))
                    .map(this::toEntity)
                    .toList();

            result.addAll(karaokeList);

            int totalCount = response.getResponse().getBody().getTotalCount();
            if (pageNo * numOfRows >= totalCount) {
                break;
            }

            pageNo++;
        }

        if (!result.isEmpty()) {
            karaokeRepository.saveAll(result);
        }
    }

    private KaraokeApiResponse callApi(int pageNo, int numOfRows) {
        String url = karaokeProperties.getBaseUrl()
                + "?serviceKey=" + karaokeProperties.getServiceKey()
                + "&cond[OPN_ATMY_GRP_CD::EQ]=5080000"
                + "&cond[SALS_STTS_CD::EQ]=01"
                + "&pageNo=" + pageNo
                + "&numOfRows=" + numOfRows;

        return restTemplate.getForObject(url, KaraokeApiResponse.class);
    }

    private List<KaraokeApiResponse.Item> extractItems(KaraokeApiResponse response) {
        if (response == null
                || response.getResponse() == null
                || response.getResponse().getBody() == null
                || response.getResponse().getBody().getItems() == null
                || response.getResponse().getBody().getItems().getItem() == null) {
            return List.of();
        }

        return response.getResponse().getBody().getItems().getItem();
    }

    private Karaoke toEntity(KaraokeApiResponseDto dto) {
        return Karaoke.builder()
                .manageNo(dto.getManageNo())
                .name(dto.getName())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .totalRooms(dto.getTotalRooms())
                .availableRooms(dto.getTotalRooms())
                .owner(null)
                .build();
    }




}
