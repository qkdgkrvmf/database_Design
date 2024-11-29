package com.database_Design.Database_Design.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Long timer_id; // 타이머 고유 식별자

    // 다대일 관계: Timer와 User 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // JSON 직렬화 시 역참조 방지
    private User user; // 타이머를 사용하는 사용자

    // 다대일 관계: Timer와 Study_group 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", nullable = true)
    private Study_group studyGroup; // 타이머가 속한 스터디 그룹 (선택사항)

    private String timer_title; // 타이머 제목
    private String timer_content; // 타이머 내용

    @Column(name = "timer_start")
    private LocalDateTime timerStart; // 타이머 시작 시간

    @Column(name = "timer_end")
    private LocalDateTime timerEnd; // 타이머 종료 시간


    private Long timer_total; // 총 학습 시간

    @Enumerated(EnumType.STRING)
    private TimerStatus status; // 타이머 상태 (STARTED, PAUSED, STOPPED)

    // Enum for Timer Status
    public enum TimerStatus {
        STARTED, PAUSED, STOPPED
    }

}