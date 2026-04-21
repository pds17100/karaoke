package com.example.karaoke.domain.ownerapplication.repository;

import com.example.karaoke.domain.ownerapplication.entity.ApplicationStatus;
import com.example.karaoke.domain.ownerapplication.entity.OwnerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerApplicationRepository extends JpaRepository<OwnerApplication, Long> {
    List<OwnerApplication> findAllByStatus(ApplicationStatus status);

    Optional<OwnerApplication> findByMemberIdAndKaraokeId(Long memberId, Long karaokeId);

    boolean existsByKaraokeIdAndStatus(Long karaokeId, ApplicationStatus status);

    List<OwnerApplication> findAllByMemberIdOrderByIdDesc(Long memberId);

    boolean existsByMemberIdAndKaraokeIdAndStatus(Long memberId, Long karaokeId, ApplicationStatus status);

}
