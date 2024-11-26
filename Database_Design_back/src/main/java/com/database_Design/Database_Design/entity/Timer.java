package com.database_Design.Database_Design.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Timer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timer_id; // 타이머 식별 기본키

    private String timer_title; // 타이머 제목 - 타이머 제목(데이터베이스…)

    private String timer_content; // 타이머 내용(ch.02~)

    @ManyToOne(fetch = FetchType.LAZY) // 사용자와의 관계 설정
    @JoinColumn(name = "user_id", nullable = false) // 외래 키 컬럼 이름
    private User user; // 사용자 정보 - 외래키

    @ManyToOne(fetch = FetchType.LAZY) // 스터디 그룹과의 관계 설정
    @JoinColumn(name = "study_group_id", nullable = true) // 외래 키 컬럼 이름
    private Study_group studyGroup; // 스터디 그룹 ID - 외래키

    @Column(name = "timer_start")
    private LocalDateTime timerStart; // 타이머 시작 시점

    @Column(name = "timer_end")
    private LocalDateTime timerEnd; // 타이머 종료 시점

    private Long timer_total; // 학습량

}
