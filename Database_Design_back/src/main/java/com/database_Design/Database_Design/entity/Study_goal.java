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
    private Long goal_id; // 기본키

    // User와의 다대일 관계 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 외래키 설정
    private User user; // 회원 ID (User 객체를 매핑) -


    private String content; // 할 일 내용

    private Date std_goal_start_date; // 할 일 시작 날짜

    private Date std_goal_end_date; // 할 일 종료 날짜

    private Boolean completed = false; // 완료 여부 (기본값: false)

    @Builder
    public Study_goal(Long goal_id, String content, Boolean completed, User user_id, Date std_goal_start_date, Date std_goal_end_date) {
        this.goal_id = goal_id;
        this.content = content;
        this.completed = completed;
        this.user = user_id;
        this.std_goal_end_date = std_goal_end_date;
        this.std_goal_start_date = std_goal_start_date;
    }
}