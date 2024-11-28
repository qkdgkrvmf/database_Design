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
    private Long goal_id; // User가 하고자 하는 '할 일' 하나하나에 부여하는 고유 식별 값

    // User와의 다대일 관계 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 외래키 설정
    private User user; // User 각각의 고유 식별 값 (User 객체를 매핑)


    private String content; // User가 하고자 하는 '할 일'의 내용

    private Date std_goal_start_date; // '할 일' 시작 날짜

    private Date std_goal_end_date; // '할 일' 종료 날짜

    private Boolean completed = false; // '할 일'의 완료 여부 (default: false / 완료 시: true / 미완료 시: false)

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