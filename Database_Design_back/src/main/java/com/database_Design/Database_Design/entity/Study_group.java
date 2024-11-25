package com.database_Design.Database_Design.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    @JoinColumn(name = "std_leader", nullable = false)
    private User std_leader; // 스터디장 - 스터디장의 login_id이 저장됨?

    private String std_name; // 스터디명

    private String std_description; // 스터디 설명

    @OneToMany(mappedBy = "study_group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> std_members; // 스터디 멤버 - User와의 관계

    private Long member_daily_std; // 그룹 멤버 개인 일일 학습량

    private Long group_daily_std; // 멤버 일일 학습량 총합 (그룹)

    private Long std_member_total; // 스터디 멤버 수

    private Date std_start_date; // LocalDateTime 사용

    private Date std_end_date; // LocalDateTime 사용

    private Study_group_post group_rule; // 스터디 규칙 - 게시판 내용(공지사항)과 매핑

    @Column(name = "stdstate") // JPA가 정확하게 std_state를 이해하게 함
    private Boolean stdstate = true; // 스터디 모집 여부(true - 모집, false - 모집X)
}
