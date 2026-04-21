package com.example.karaoke.domain.karaoke.repository;

import com.example.karaoke.domain.karaoke.entity.Karaoke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KaraokeRepository extends JpaRepository<Karaoke, Long> {
    //  N+1 해결
    List<Karaoke> findByManageNoIn(List<String> manageNos);

    Optional<Karaoke> findByOwnerId(Long ownerId);

//    boolean existsByManageNo(String manageNo);
//    Optional<Karaoke> findByManageNo(String manageNo);

}
