package com.example.karaoke.domain.karaoke.kakao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "external.api.kakao")
public class KakaoProperties {
    private String apiKey;

}
