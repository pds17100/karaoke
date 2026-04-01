package com.example.karaoke.domain.karaoke.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "external.api.karaoke")
public class KaraokeProperties {
    private String baseUrl;
    private String serviceKey;

}
