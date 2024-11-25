package com.database_Design.Database_Design.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor // 기본 생성자
public class User {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id; // 기본 키

    private String loginId; // 사용자 아이디

    private String password; // 비밀번호

    private String passwordCheck; // 비밀번호 확인

    private String name; // 이름

    private Long birth; // 생년월일

    private String phoneNumber; // 전화번호

    private String grade; // 등급

    private Long point; // 포인트

    private Long total_study; // 학습량 - timer에서 외래키로



    @Builder // Builder 패턴 지원
    public User(Long id, String userid, String password, String passwordCheck, String name, Long birth, String phoneNumber) {
        this.id = id;
        this.loginId = userid;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.name = name;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
    }

    // Study_goal과의 관계 (1:N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_goal> studyGoals = new ArrayList<>();
}