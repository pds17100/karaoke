package com.example.karaoke.domain.karaoke.controller;

import com.example.karaoke.domain.auth.dto.CustomOAuth2User;
import com.example.karaoke.domain.karaoke.dto.OwnerKaraokeResponseDto;
import com.example.karaoke.domain.karaoke.dto.OwnerRoomCountUpdateRequestDto;
import com.example.karaoke.domain.karaoke.service.OwnerKaraokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owner/karaoke")
public class OwnerKaraokeController {

    private final OwnerKaraokeService ownerKaraokeService;

    @GetMapping
    public ResponseEntity<OwnerKaraokeResponseDto> getMyKaraoke(
            @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(ownerKaraokeService.getMyKaraoke(user.getMemberId()));
    }

    @PatchMapping("/available-rooms/increase")
    public ResponseEntity<OwnerKaraokeResponseDto> increaseAvailableRooms(
            @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(ownerKaraokeService.increaseAvailableRooms(user.getMemberId()));
    }

    @PatchMapping("/available-rooms/decrease")
    public ResponseEntity<OwnerKaraokeResponseDto> decreaseAvailableRooms(
            @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseEntity.ok(ownerKaraokeService.decreaseAvailableRooms(user.getMemberId()));
    }

    @PatchMapping("/room-count")
    public ResponseEntity<OwnerKaraokeResponseDto> updateRoomCount(
            @AuthenticationPrincipal CustomOAuth2User user,
            @RequestBody OwnerRoomCountUpdateRequestDto requestDto) {

        return ResponseEntity.ok(ownerKaraokeService.updateRoomCount(user.getMemberId(), requestDto));
    }


}
