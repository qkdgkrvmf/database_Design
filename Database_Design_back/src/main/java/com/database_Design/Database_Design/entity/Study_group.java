//package com.database_Design.Database_Design.entity;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Getter
//@Setter
//@Service
//@Entity
//@NoArgsConstructor
//public class Study_group {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long std_id; // 스터디 그룹 각각에 부여하는 고유 식별 값
//
//    @ManyToOne(fetch = FetchType.LAZY) // 스터디장(User와의 관계)
//    @JoinColumn(name = "std_leader", nullable = false)
//    private User std_leader; // 스터디 그룹 리더의 user id
//
//    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Study_group_post> posts = new ArrayList<>(); // 스터디 그룹이 가지는 게시판에 존재하는 게시글들의 리스트
//
//    private String std_name; // 스터디 그룹의 이름
//
//    private String std_description; // 스터디 그룹에 대한 설명
//
//    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Study_group_member> std_members = new ArrayList<>(); // 스터디 그룹에 소속한 멤버의 리스트
//
//    private Long member_daily_std; // 스터디 그룹 내의 멤버 각각의 일일 학습량
//
//    private Long group_daily_std; // 스터디 그룹 내의 멤버들의 일일 학습량의 총합 (그룹)
//
//    private Long std_member_total; // 스터디 그룹 내의 멤버들의 현재 총 인원 수
//
//    private Date std_start_date; // 스터디 그룹이 진행 시작하는 날짜 (LocalDateTime 사용)
//
//    private Date std_end_date; // 스터디 그룹이 진행 종료되는 날짜 (LocalDateTime 사용)
//
//    private String group_rule; // 스터디 공지사항
//
//    @Column(name = "stdstate") // JPA가 정확하게 std_state를 이해하게 함
//    private Boolean stdstate = true; // 스터디 모집 여부 (모집 중: true / 모집 종료: false)
//
//}
package com.database_Design.Database_Design.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Study_group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long std_id; // 스터디 그룹의 고유 식별자

    // 다대일 관계: Study_group과 User(스터디장) 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "std_leader", nullable = false)
    private User std_leader; // 스터디 그룹의 리더

    // 일대다 관계: Study_group과 Study_group_post 연결
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_group_post> posts = new ArrayList<>(); // 스터디 그룹의 게시글 목록

    // 일대다 관계: Study_group과 Study_group_member 연결
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_group_member> std_members = new ArrayList<>(); // 스터디 그룹의 멤버 목록

    // 일대다 관계: Study_group과 Timer 연결
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timer> timers = new ArrayList<>(); // 스터디 그룹의 타이머 목록

    private String std_name; // 스터디 그룹 이름
    private String std_description; // 스터디 그룹 설명
    private Long member_daily_std; // 멤버 일일 학습량
    private Long group_daily_std; // 그룹 일일 총 학습량
    private Long std_member_total; // 현재 그룹 멤버 총 수
    private Date std_start_date; // 스터디 그룹 시작 날짜
    private Date std_end_date; // 스터디 그룹 종료 날짜
    private String group_rule; // 스터디 그룹 규칙

    @Column(name = "stdstate")
    private Boolean stdstate = true; // 스터디 모집 상태
}