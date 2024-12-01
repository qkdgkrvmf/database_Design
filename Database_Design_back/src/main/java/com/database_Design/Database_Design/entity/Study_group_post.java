package com.database_Design.Database_Design.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Study_group_post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long group_post_id;


    // 다대일 관계: Study_group_post와 Study_group 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Study_group studyGroup; // 게시글이 속한 스터디 그룹


     @ManyToOne
     @JoinColumn(name = "group_post_writer", referencedColumnName = "member_id", nullable = false)
     private Study_group_member group_post_writer;


    @Column(nullable = false)
    private String group_post_content; // 게시글 내용
}