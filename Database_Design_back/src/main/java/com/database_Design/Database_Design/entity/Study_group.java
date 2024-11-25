package com.database_Design.Database_Design.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Service
@Entity
@NoArgsConstructor
public class Study_group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long std_id; // 스터디 식별 기본키

    @ManyToOne(fetch = FetchType.LAZY) // 스터디장(User와의 관계)
    @JoinColumn(name = "std_leader_id", nullable = false)
    private User std_leader; // 스터디장

    private String std_name; // 스터디명

    private String std_member; // 스터디 멤버 - 외래키로 받아오기

    private Long member_daily_std; // 그룹 멤버 개인 일일 학습량

    private Long group_daily_std; // 멤버 일일 학습량 총합 (그룹)

    private Long std_member_total; // 스터디 멤버 수

    private Date std_start_date; // LocalDateTime 사용

    private Date std_end_date; // LocalDateTime 사용
}
