package com.example.karaoke.domain.karaoke;

import com.example.karaoke.domain.karaoke.dto.KakaoTranscodeResponse;
import com.example.karaoke.domain.karaoke.kakao.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class
CoordinateConverter {

    private final RestTemplate restTemplate;
    private final KakaoProperties kakaoProperties;

    public ConvertedCoordinate convertTmToWgs84(double x, double y) {
        String url = "https://dapi.kakao.com/v2/local/geo/transcoord.json" +
                "?x=" + x +
                "&y=" + y +
                "&input_coord=TM" +
                "&output_coord=WGS84";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoProperties.getApiKey());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoTranscodeResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KakaoTranscodeResponse.class
        );

        KakaoTranscodeResponse body = response.getBody();

        if (body == null || body.getDocuments() == null || body.getDocuments().isEmpty()) {
            throw new IllegalStateException("카카오 좌표 변환 응답이 비어 있습니다.");
        }

        KakaoTranscodeResponse.Document document = body.getDocuments().get(0);

        return new ConvertedCoordinate(document.getY(), document.getX());
    }

    public record ConvertedCoordinate(double latitude, double longitude) {
    }


}
