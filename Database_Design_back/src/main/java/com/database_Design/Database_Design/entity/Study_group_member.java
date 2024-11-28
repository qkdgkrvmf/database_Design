package com.database_Design.Database_Design.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Study_group_member {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id; // 스터디 그룹 식별자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false) // Study_group과 연결
    private Study_group studyGroup; // 스터디 그룹 - 스터디 식별

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // User의 외래 키
    private User user_id; // User 엔티티와 매핑 - 스터디 멤버 사용자

//    private String join; // 참여 상태

    @OneToMany(mappedBy = "group_post_writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_group_post> posts = new ArrayList<>(); // 사용자가 작성한 게시글 리스트
    private String role; // 역할
}
