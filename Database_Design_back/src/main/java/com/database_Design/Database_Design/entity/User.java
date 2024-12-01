package com.database_Design.Database_Design.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // 직렬화 방지
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자의 고유 식별자 ID

    private String loginId; // 로그인에 사용되는 아이디
    private String password; // 사용자 비밀번호
    private String passwordCheck; // 비밀번호 확인
    private String name; // 사용자 이름
    private LocalDate birth; // 생년월일
    private String phoneNumber; // 전화번호
    private String grade = "알"; // 사용자 등급 (알-금간알-깨진알1-깨진알2-병아리-닭)
    private Long point = 0L; // 사용자 포인트
    private Long total_study = 0L; // 사용자 총 학습량

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // study_member는 study_group와 user의 중간 테이블 역할 따라서 user 입장에서 어떤 그룹에 속했는지 확인하려면 해당 필드가 필요
    @JsonIgnore // Study_group_member 리스트 무시
    private List<Study_group_member> studyGroupMembers = new ArrayList<>();

    // 다대다 관계: User와 Study_group 연결
    @ManyToMany
    @JoinTable(
            name = "user_study_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "study_group_id")
    )
    private List<Study_group> study_group = new ArrayList<>(); // 사용자가 속한 스터디 그룹 목록

    // 일대다 관계: User와 Study_goal 연결
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // 순환참조 방지 - 직렬화
    private List<Study_goal> studyGoals = new ArrayList<>(); // 사용자의 학습 목표 목록

    // 일대다 관계: User와 Timer 연결
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // JSON 직렬화 시 참조 방향 설정
    private List<Timer> timers = new ArrayList<>(); // 사용자의 타이머 목록

    @Builder
    public User(Long id, String loginId, String password, String passwordCheck,
                String name, LocalDate birth, String phoneNumber,
                String grade, Long point, Long total_study) {
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
}