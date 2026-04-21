package com.example.karaoke.domain.ownerapplication.service;

import com.example.karaoke.domain.file.FileStoreService;
import com.example.karaoke.domain.karaoke.entity.Karaoke;
import com.example.karaoke.domain.karaoke.repository.KaraokeRepository;
import com.example.karaoke.domain.member.entity.Member;
import com.example.karaoke.domain.member.entity.MemberRole;
import com.example.karaoke.domain.member.repository.MemberRepository;
import com.example.karaoke.domain.ownerapplication.dto.OwnerApplicationAdminListResponseDto;
import com.example.karaoke.domain.ownerapplication.dto.OwnerApplicationCreateRequestDto;
import com.example.karaoke.domain.ownerapplication.dto.OwnerApplicationResponseDto;
import com.example.karaoke.domain.ownerapplication.entity.ApplicationStatus;
import com.example.karaoke.domain.ownerapplication.entity.OwnerApplication;
import com.example.karaoke.domain.ownerapplication.repository.OwnerApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerApplicationService {
    private final OwnerApplicationRepository ownerApplicationRepository;
    private final MemberRepository memberRepository;
    private final KaraokeRepository karaokeRepository;
    private final FileStoreService fileStoreService;

    /**
     * 로그인한 사용자가 노래방 사장 등록 신청 (파일 처리 버전)
     */
    @Transactional
    public OwnerApplicationResponseDto apply(Long memberId, OwnerApplicationCreateRequestDto requestDto, MultipartFile businessLicenseImage) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Karaoke karaoke = karaokeRepository.findById(requestDto.getKaraokeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 노래방입니다."));

        validateAlreadyOwner(member);
        validateDuplicateApplication(member, karaoke);
        validateAlreadyOwnedKaraoke(karaoke);

        String businessLicenseImageUrl = fileStoreService.store(businessLicenseImage);

        OwnerApplication ownerApplication = OwnerApplication.create(
                member,
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPhone(),
                requestDto.getBusinessNumber(),
                businessLicenseImageUrl,
                karaoke
        );

        OwnerApplication savedOwnerApplication = ownerApplicationRepository.save(ownerApplication);
        return new OwnerApplicationResponseDto(savedOwnerApplication);
    }


    /**
     * 신청서 단건 조회
     */
    public OwnerApplicationResponseDto getApplication(Long applicationId) {
        OwnerApplication ownerApplication = ownerApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청서입니다."));

        return new OwnerApplicationResponseDto(ownerApplication);
    }

    /**
     *  신청 내역 조회
     */
    public List<OwnerApplicationResponseDto> getMyApplications(Long memberId) {
        return ownerApplicationRepository.findAllByMemberIdOrderByIdDesc(memberId)
                .stream()
                .map(OwnerApplicationResponseDto::new)
                .toList();
    }

    /**
     *
     */
    public List<OwnerApplicationAdminListResponseDto> getApplicationsByStatus(ApplicationStatus status) {
        List<OwnerApplication> applications;

        if (status == null) {
            applications = ownerApplicationRepository.findAll();
        } else {
            applications = ownerApplicationRepository.findAllByStatus(status);
        }

        return applications.stream()
                .map(OwnerApplicationAdminListResponseDto::from)
                .toList();
    }


    /**
     * 관리자 승인
     * - 신청 상태 APPROVED
     * - Member role USER -> OWNER
     * - Karaoke owner 연결
     */
    @Transactional
    public void approve(Long applicationId) {
        OwnerApplication ownerApplication = ownerApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청서입니다."));

        if (!ownerApplication.isPending()) {
            throw new IllegalStateException("대기중인 신청서만 승인할 수 있습니다.");
        }

        Member member = ownerApplication.getMember();
        Karaoke karaoke = ownerApplication.getKaraoke();

        if (karaoke.getOwner() != null) {
            throw new IllegalStateException("이미 소유자가 등록된 노래방입니다.");
        }

        ownerApplication.approve();
        member.changeRole(MemberRole.OWNER);
        karaoke.assignOwner(member);
    }

    /**
     * 관리자 거절 (거절 사유 추가)
     */
    @Transactional
    public void reject(Long applicationId, String rejectReason) {
        OwnerApplication ownerApplication = ownerApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청서입니다."));

        if (!ownerApplication.isPending()) {
            throw new IllegalStateException("대기중인 신청서만 거절할 수 있습니다.");
        }

        if (rejectReason == null || rejectReason.trim().isEmpty()) {
            throw new IllegalArgumentException("반려 사유를 입력해주세요.");
        }

        ownerApplication.reject(rejectReason);
    }

    /**
     * 이미 OWNER 권한이면 다시 신청 불가
     */
    private void validateAlreadyOwner(Member member) {
        if (member.getRole() == MemberRole.OWNER) {
            throw new IllegalStateException("이미 사장 권한을 가진 회원입니다.");
        }
    }

    /**
     * 같은 회원이 같은 노래방에 중복 신청하는 것 방지
     */
    private void validateDuplicateApplication(Member member, Karaoke karaoke) {
        boolean exists = ownerApplicationRepository
                .existsByMemberIdAndKaraokeIdAndStatus(
                        member.getId(),
                        karaoke.getId(),
                        ApplicationStatus.PENDING
                );

        if (exists) {
            throw new IllegalStateException("이미 대기 중인 신청이 있습니다.");
        }
    }


    /**
     * 이미 owner가 배정된 노래방인지 확인
     */
    private void validateAlreadyOwnedKaraoke(Karaoke karaoke) {
        if (karaoke.getOwner() != null) {
            throw new IllegalStateException("이미 사장이 등록된 노래방입니다.");
        }
    }



}
