package com.database_Design.Database_Design.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore // Study_group 필드 무시 - 순환 참조 방지
    private Study_group studyGroup; // 해당 멤버가 속한 스터디 그룹

    // 다대일 관계: Study_group_member와 User 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 해당 멤버의 사용자 정보


    // 일대다 관계: Study_group_member와 Study_group_post 연결
    @OneToMany(mappedBy = "group_post_writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_group_post> posts = new ArrayList<>(); // 멤버가 작성한 게시글 목록

    private String role; // 스터디 그룹 내 역할 (예: 스터디장, 스터디원)


}