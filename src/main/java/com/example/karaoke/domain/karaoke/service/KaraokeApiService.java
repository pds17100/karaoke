package com.example.karaoke.domain.karaoke.service;

import com.example.karaoke.domain.karaoke.CoordinateConverter;
import com.example.karaoke.domain.karaoke.config.KaraokeProperties;
import com.example.karaoke.domain.karaoke.dto.KaraokeApiResponse;
import com.example.karaoke.domain.karaoke.dto.KaraokeApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 공공데이터 API 조회 + 좌표변환 + DTO 변환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KaraokeApiService {

    private final RestTemplate restTemplate;
    private final KaraokeProperties karaokeProperties;
    private final CoordinateConverter coordinateConverter;

    /**
     * 공공데이터 전체 조회 후 DTO 반환
     */
    public List<KaraokeApiResponseDto> fetchKaraokes(String regionCode) {
        int pageNo = 1;
        int numOfRows = 100;

        List<KaraokeApiResponseDto> result = new ArrayList<>();

        while (true) {
            KaraokeApiResponse response = callApi(pageNo, numOfRows, regionCode);
            List<KaraokeApiResponse.Item> items = extractItems(response);

            if (items.isEmpty()) {
                break;
            }

            List<KaraokeApiResponseDto> dtos = items.stream()
                    .map(this::toDto)
                    .filter(dto -> dto != null)
                    .filter(dto -> dto.getManageNo() != null && !dto.getManageNo().isBlank())
                    .toList();

            result.addAll(dtos);

            int totalCount = response.getResponse().getBody().getTotalCount();
            if (pageNo * numOfRows >= totalCount) {
                break;
            }

            pageNo++;
        }

        return result;
    }


    /**
     *  지역 코드 넘기기
     */
    private KaraokeApiResponse callApi(int pageNo, int numOfRows, String regionCode) {
        String url = karaokeProperties.getBaseUrl()
                + "?serviceKey=" + karaokeProperties.getServiceKey()
                + "&cond[OPN_ATMY_GRP_CD::EQ]=" + regionCode
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

    private KaraokeApiResponseDto toDto(KaraokeApiResponse.Item item) {
        double x = KaraokeApiResponseDto.parseCoordinate(item.getCRD_INFO_X());
        double y = KaraokeApiResponseDto.parseCoordinate(item.getCRD_INFO_Y());

        if (x == 0.0 || y == 0.0) {
            // 예외 대신 null 반환 → filter로 걸러냄
            log.warn("좌표 정보 없음, 건너뜀. manageNo={}", item.getMNG_NO());
            return null;
        }

        CoordinateConverter.ConvertedCoordinate converted =
                coordinateConverter.convertTmToWgs84(x, y);

        return new KaraokeApiResponseDto(
                item.getMNG_NO(),
                item.getBPLC_NM(),
                item.getROAD_NM_ADDR(),
                converted.latitude(),
                converted.longitude(),
                KaraokeApiResponseDto.parseRoomCount(item.getKAROOM_RM_CNT())
        );
    }



}