package com.example.karaoke.domain.ownerapplication.controller;

import com.example.karaoke.domain.auth.dto.CustomOAuth2User;
import com.example.karaoke.domain.ownerapplication.dto.OwnerApplicationCreateRequestDto;
import com.example.karaoke.domain.ownerapplication.dto.OwnerApplicationResponseDto;
import com.example.karaoke.domain.ownerapplication.service.OwnerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owner-application")
public class OwnerApplicationController {

    private final OwnerApplicationService ownerApplicationService;

    /**
     *  노래방 사장 승인 신청 내역
     */
    @GetMapping("/me")
    public ResponseEntity<List<OwnerApplicationResponseDto>> getMyApplications(
            @AuthenticationPrincipal CustomOAuth2User user
    ) {
        return ResponseEntity.ok(
                ownerApplicationService.getMyApplications(user.getMemberId())
        );
    }

    //파일 처리 버전
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OwnerApplicationResponseDto> apply(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
            @RequestPart("request") OwnerApplicationCreateRequestDto requestDto,
            @RequestPart("businessLicenseImage") MultipartFile businessLicenseImage
    ) {
        if (customOAuth2User == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        OwnerApplicationResponseDto response =
                ownerApplicationService.apply(
                        customOAuth2User.getMemberId(),
                        requestDto,
                        businessLicenseImage
                );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }



}

