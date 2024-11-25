package com.database_Design.Database_Design.entity;


import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Timer {
    private Long timer_id; // 타이머 식별 기본키

    private String timer_title; // 타이머 제목 - 타이머 제목(데이터베이스…)

    private String timer_content; // 타이머 내용(ch.02~)

    private Long user_id; // 사용자 정보 - 외래키

    private LocalDateTime timer_start; // 타이머 시작 일시

    private LocalDateTime timer_end; // 타이머 종료 일시

    private Long timer_total; // 학습량

}
