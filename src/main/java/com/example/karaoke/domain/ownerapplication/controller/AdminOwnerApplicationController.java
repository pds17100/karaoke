package com.example.karaoke.domain.ownerapplication.controller;

import com.example.karaoke.domain.ownerapplication.dto.OwnerApplicationAdminListResponseDto;
import com.example.karaoke.domain.ownerapplication.dto.OwnerApplicationResponseDto;
import com.example.karaoke.domain.ownerapplication.dto.RejectRequestDto;
import com.example.karaoke.domain.ownerapplication.entity.ApplicationStatus;
import com.example.karaoke.domain.ownerapplication.service.OwnerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/owner-applications")
public class AdminOwnerApplicationController {

    private final OwnerApplicationService ownerApplicationService;

    //노래방이름, 이름, 연락처, 상태
    @GetMapping
    public ResponseEntity<List<OwnerApplicationAdminListResponseDto>> getApplications(
            @RequestParam(required = false) ApplicationStatus status) {
        return ResponseEntity.ok(ownerApplicationService.getApplicationsByStatus(status));
    }

    //
    @GetMapping("/{applicationId}")
    public ResponseEntity<OwnerApplicationResponseDto> getApplication(
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.ok(ownerApplicationService.getApplication(applicationId));
    }

    @PostMapping("/{applicationId}/approve")
    public ResponseEntity<Void> approve(@PathVariable Long applicationId) {
        ownerApplicationService.approve(applicationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{applicationId}/reject")
    public ResponseEntity<Void> reject(@PathVariable Long applicationId, @RequestBody RejectRequestDto rejectRequestDto) {
        ownerApplicationService.reject(applicationId,rejectRequestDto.getRejectReason());
        return ResponseEntity.ok().build();
    }

}
