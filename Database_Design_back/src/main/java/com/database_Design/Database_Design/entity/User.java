//package com.database_Design.Database_Design.entity;
//
//
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//@NoArgsConstructor // 기본 생성자
//public class User {
//
//    @Id // 기본키
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
//    private Long id; // 기본 키
//
//    private String loginId; // 사용자 아이디
//
//    private String password; // 비밀번호
//
//    private String passwordCheck; // 비밀번호 확인
//
//    private String name; // 이름
//
//    private LocalDate birth; // 생년월일
//
//    private String phoneNumber; // 전화번호
//
//    private String grade = "알"; // 등급 (알-금간알-깨진알1-깨진알2-병아리-닭)
//
//    private Long point = 0L; // 포인트
//
//    private Long total_study=0L; // 사용자 학습량
//
//    // Study_group과 다대다 관계 (N:M)
//    @ManyToMany
//    @JoinTable(
//            name = "user_study_group", // 중간 테이블 이름 - 중간 테이블은  User와 Study_group 엔티티를 연결(실제 DB에 생성)
//            // User와 Study_group은 직접적으로 서로 연결되지 않고, 중간 테이블인 user_study_group을 통해 연결됨.
//            joinColumns = @JoinColumn(name = "user_id"), // User 외래 키
//            inverseJoinColumns = @JoinColumn(name = "study_group_id") // Study_group 외래 키
//    )
//    private List<Study_group> study_group = new ArrayList<>(); // 사용자 소속 스터디 그룹 리스트
//
//    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Study_group_member> studyGroupMembers = new ArrayList<>(); // 사용자가 스터디 그룹의 회원으로 참여하는 관계 관리를 위한 변수 선언(사용자가 여러 그룹의 멤버로 소속될 수 있는 구조)
//
//    @Builder // Builder 패턴 지원
//    public User(Long id, String loginId, String password, String passwordCheck, String name, LocalDate birth, String phoneNumber, String grade, Long point, Long total_study) {
//        this.id = id;
//        this.loginId = loginId;
//        this.password = password;
//        this.passwordCheck = passwordCheck;
//        this.name = name;
//        this.birth = birth;
//        this.phoneNumber = phoneNumber;
//        this.grade = grade;
//        this.point = point;
//        this.total_study = total_study;
//    }
//
//    // Study_goal과의 관계 (1:N)
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Study_goal> studyGoals = new ArrayList<>(); // 사용자의 TodoList(할 일) 리스트
//}
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
@NoArgsConstructor
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

    // 다대다 관계: User와 Study_group 연결
    @ManyToMany
    @JoinTable(
            name = "user_study_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "study_group_id")
    )
    private List<Study_group> study_group = new ArrayList<>(); // 사용자가 속한 스터디 그룹 목록

    // 일대다 관계: User와 Study_group_member 연결
    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_group_member> studyGroupMembers = new ArrayList<>(); // 사용자의 스터디 그룹 멤버십 목록

    // 일대다 관계: User와 Study_goal 연결
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_goal> studyGoals = new ArrayList<>(); // 사용자의 학습 목표 목록

    // 일대다 관계: User와 Timer 연결
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
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