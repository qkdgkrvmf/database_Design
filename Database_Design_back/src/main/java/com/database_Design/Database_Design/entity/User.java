package com.database_Design.Database_Design.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    private LocalDate birth; // 생년월일

    private String phoneNumber; // 전화번호

    private String grade = "알"; // 등급 (알-금간알-깨진알1-깨진알2-병아리-닭)

    private Long point = 0L; // 포인트

    private Long total_study=0L; // 학습량 - timer에서 외래키로 (분 단위)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group") // 외래 키 이름
    private Study_group study_group; // 스터디 그룹과의 관계

    @Builder // Builder 패턴 지원
    public User(Long id, String loginId, String password, String passwordCheck, String name, LocalDate birth, String phoneNumber, String grade, Long point, Long total_study) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.name = name;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.point = point;
        this.total_study = total_study;
    }

    // Study_goal과의 관계 (1:N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_goal> studyGoals = new ArrayList<>();
}