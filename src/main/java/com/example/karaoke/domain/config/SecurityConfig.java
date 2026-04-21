package com.example.karaoke.domain.config;

import com.example.karaoke.domain.auth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //rest api 설정
                .cors(cors -> {
                })
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                //  request 인증, 인가 설정
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/",
                                        "/error",
                                        "/oauth2/**",
                                        "/login/**",
                                        // Swagger
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/admin/create").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/karaokes/load").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/owner/**").hasRole("OWNER")

                        .requestMatchers(HttpMethod.GET, "/api/karaokes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/member/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/karaokes/**").permitAll()

                        .anyRequest().authenticated()
                )
                //  oauth 설정
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("http://localhost:3001/", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 프론트에서 호출하는 URL
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200); // React에서는 리다이렉트 대신 200 응답이 필요
                        })
                        .invalidateHttpSession(true) // 세션 삭제
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3001"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}