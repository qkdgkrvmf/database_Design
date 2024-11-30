package com.database_Design.Database_Design.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Study_goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goal_id; // 학습 목표 고유 식별자

    // 다대일 관계: Study_goal과 User 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 목표를 설정한 사용자

    private String content; // 학습 목표 내용
    private Date std_goal_start_date; // 목표 시작 날짜
    private Date std_goal_end_date; // 목표 종료 날짜
    private Boolean completed = false; // 목표 완료 상태

    @Builder
    public Study_goal(Long goal_id, String content, Boolean completed,
                      User user, Date std_goal_start_date, Date std_goal_end_date) {
        this.goal_id = goal_id;
        this.content = content;
        this.completed = completed;
        this.user = user;
        this.std_goal_end_date = std_goal_end_date;
        this.std_goal_start_date = std_goal_start_date;
    }
}