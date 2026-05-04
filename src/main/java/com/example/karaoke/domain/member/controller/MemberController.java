package com.example.karaoke.domain.member.controller;

import com.example.karaoke.domain.auth.dto.CustomOAuth2User;
import com.example.karaoke.domain.member.dto.MeResponseDto;
import com.example.karaoke.domain.member.entity.Member;
import com.example.karaoke.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/me")
    public ResponseEntity<MeResponseDto> me(@AuthenticationPrincipal CustomOAuth2User user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        Member member = memberRepository.findById(user.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

        MeResponseDto meResponseDto = new MeResponseDto(
                member.getRole() != null ? member.getRole().name() : null);

        return ResponseEntity.ok(meResponseDto);
    }


}

