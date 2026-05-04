package com.example.karaoke.domain.karaoke.entity;

import com.example.karaoke.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Karaoke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "karaoke_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String manageNo;    // 공공데이터 관리번호(MNG_NO)

    private String name;        // 노래방 이름
    private String address;     // 주소

    private double latitude;    // 위도
    private double longitude;   // 경도

    private int totalRooms;     // 총 방 개수
    private int availableRooms; // 남은 방 개수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Member owner;       // 사장 (없을 수도 있음)


    @Builder
    public Karaoke(String manageNo, String name, String address, double latitude, double longitude,
                   int totalRooms, int availableRooms, Member owner) {
        this.manageNo = manageNo;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.owner = owner;
    }


    /**
     * 비즈니스 메서드
     */
    public void increaseAvailableRooms() {
        if (this.availableRooms >= this.totalRooms) {
            throw new IllegalStateException("남은 방 수는 총 방 수를 초과할 수 없습니다.");
        }
        this.availableRooms++;
    }

    public void decreaseAvailableRooms() {
        if (this.availableRooms <= 0) {
            throw new IllegalStateException("남은 방 수는 0보다 작을 수 없습니다.");
        }
        this.availableRooms--;
    }

    public void updateRoomCount(int totalRooms, int availableRooms) {
        if (totalRooms < 0 || availableRooms < 0 || availableRooms > totalRooms) {
            throw new IllegalArgumentException("방 개수가 올바르지 않습니다.");
        }
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
    }

    /**
     * 공공데이터 기준 기본 정보 갱신
     */
    public void updateBasicInfo(String name, String address, double latitude, double longitude, int totalRooms) {
        if (totalRooms < 0) {
            throw new IllegalArgumentException("총 방 개수는 0보다 작을 수 없습니다.");
        }

        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalRooms = totalRooms;

        if (this.availableRooms > totalRooms) {
            this.availableRooms = totalRooms;
        }
    }


    /**
     *  승인되면 해당 노래방에 owner 연결
     */
    public void assignOwner(Member owner) {
        this.owner = owner;
    }


}
