//package com.database_Design.Database_Design.entity;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Date;
//
//@Getter
//@Setter
//@Entity
//@NoArgsConstructor
//public class Study_group_post {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long group_post_id; // 스터디 그룹 게시글 고유 ID (규칙 1, 2…)
//
//    @ManyToOne(fetch = FetchType.LAZY) // N:1
//    @JoinColumn(name = "group_id", nullable = false, insertable = false, updatable = false) // study_group과 연결
//    private Study_group studyGroup; // 해당 게시글이 어느 스터디 그룹에 속하는지를 판단하기 위한 변수 선언
//
////    @Column(name = "group_id")
////    private Long group_id; // 게시판 식별 기본키 - 기본키+외래키
//
//    private String groupNotice; // 스터디 그룹 공지사항
//
//    @Column(nullable = false)
//    private String group_post_content; // 스터디 그룹 게시글 내용
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "writer_id", nullable = false) // 작성자와 매핑
//    private Study_group_member group_post_writer; // 스터디 그룹의 게시글 작성자(스터디 멤버만 가능)
//
//    @Column(nullable = false)
//    private Date group_post_date; // 스터디그룹 게시글 작성일자
//}
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

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long group_post_id; // 스터디 그룹 게시글 고유 식별자

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long group_post_id;


    // 다대일 관계: Study_group_post와 Study_group 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Study_group studyGroup; // 게시글이 속한 스터디 그룹

    // 다대일 관계: Study_group_post와 Study_group_member 연결 (기존)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "writer_id", nullable = false)
//    private Study_group_member group_post_writer; // 게시글 작성자


     @ManyToOne
      @JoinColumn(name = "group_post_writer", referencedColumnName = "member_id", nullable = false)
      private Study_group_member group_post_writer;


    @Column(nullable = false)
    private String group_post_content; // 게시글 내용

    @Column(nullable = false)
    private Date group_post_date; // 게시글 작성 날짜

    //private String groupNotice; // 스터디 그룹 공지사항
}