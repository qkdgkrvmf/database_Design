//package com.database_Design.Database_Design.entity;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//public class Study_group_member {
//
//    @Id // 기본키
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long member_id; // 스터디 그룹 식별자
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "group_id", nullable = false) // Study_group과 연결
//    private Study_group studyGroup; // 스터디 그룹 - 스터디 식별(멤버가 어떤 스터디 그룹에 소속되어있는지 정확하게 하기 위함 >> 데이터 무결성 유지)
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false) // User의 외래 키
//    private User user_id; // User 엔티티와 매핑 - 스터디 멤버 사용자(해당 멤버가 어떤 사용자를 나타내는지 정확하게 하기 위함 >> user_id를 통해 특정 사용자가 속한 그룹 리스트 확인 가능)
//
////    private String join; // 참여 상태
//
//    @OneToMany(mappedBy = "group_post_writer", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Study_group_post> posts = new ArrayList<>(); // 스터디 그룹 멤버가 작성한 게시글 리스트와 매핑하기 위한 변수 선언
//
//    private String role; // 스터디 내 역할(스터디장, 스터디원)
//}
package com.database_Design.Database_Design.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Study_group_member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id; // 스터디 그룹 멤버 고유 식별자

    // 다대일 관계: Study_group_member와 Study_group 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Study_group studyGroup; // 해당 멤버가 속한 스터디 그룹

    // 다대일 관계: Study_group_member와 User 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id; // 해당 멤버의 사용자 정보

    // 일대다 관계: Study_group_member와 Study_group_post 연결
    @OneToMany(mappedBy = "group_post_writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_group_post> posts = new ArrayList<>(); // 멤버가 작성한 게시글 목록

    private String role; // 스터디 그룹 내 역할 (예: 스터디장, 스터디원)
}