package com.database_Design.Database_Design.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Study_group_post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long group_post_id; // 스터디그룹 게시글 고유 ID (규칙 1, 2…)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false) // study_group과 연결
    private Study_group studyGroup;

//    @Column(name = "group_id")
//    private Long group_id; // 게시판 식별 기본키 - 기본키+외래키

    private String groupNotice; // 스터디그룹 공지사항

    @Column(nullable = false)
    private String group_post_content; // 스터디그룹 게시글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false) // 작성자와 매핑
    private Study_group_member group_post_writer; // 게시글 작성자

    @Column(nullable = false)
    private Date group_post_date; // 스터디그룹 게시글 작성일자
}
