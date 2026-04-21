package com.example.karaoke.domain.karaoke.config;

import com.example.karaoke.domain.karaoke.kakao.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({KaraokeProperties.class, KakaoProperties.class})
public class PropertiesConfig {

}
