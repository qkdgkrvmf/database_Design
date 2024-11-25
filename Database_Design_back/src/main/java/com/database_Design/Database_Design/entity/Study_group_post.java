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
    private Long group_id; // 게시판 식별 기본키 - 기본키+외래키

    private String group_notice; // 스터디그룹 공지사항

    private Long group_post_id; // 스터디그룹 게시글 고유 ID (규칙 1, 2…)

    private String group_post_content; // 스터디그룹 게시글 내용

    private String group_post_writer; // 스터디그룹 게시글 작성자 - 외래키(Study_member)

    private Date group_post_date; // 스터디그룹 게시글 작성일자
}
