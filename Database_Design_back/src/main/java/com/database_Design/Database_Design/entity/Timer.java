//package com.database_Design.Database_Design.entity;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@Entity
//public class Timer {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long timer_id; // 타이머 식별 기본키
//
//    private String timer_title; // 타이머 제목 - 타이머 제목(데이터베이스…)
//
//    private String timer_content; // 타이머 내용(ch.02~)
//
//    @ManyToOne(fetch = FetchType.LAZY) // 사용자와의 관계 설정
//    @JoinColumn(name = "user_id", nullable = false) // 외래 키 컬럼 이름
//    private User user; // 타이머를 사용하고 있는 사용자를 매핑하기 위한 변수 선언
//
//    @ManyToOne(fetch = FetchType.LAZY) // 스터디 그룹과의 관계 설정
//    @JoinColumn(name = "study_group_id", nullable = true) // 외래 키 컬럼 이름
//    private Study_group studyGroup; // 타이머를 사용하고 있는 스터디 그룹을 매핑하기 위한 변수 선언
//
//    @Column(name = "timer_start")
//    private LocalDateTime timerStart; // 타이머 시작 시점
//
//    @Column(name = "timer_end")
//    private LocalDateTime timerEnd; // 타이머 종료 시점
//
//    private Long timer_total; // 학습량(타이머 종료시점 - 타이머 시작 시점)
//
//}
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
    private Long timer_id; // 타이머 고유 식별자

    // 다대일 관계: Timer와 User 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
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
}