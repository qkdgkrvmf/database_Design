package com.database_Design.Database_Design.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Study_group_member {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id; // 스터디 그룹 식별자

    private Long std_id;; // 스터디 식별 기본키 - 외래키로 받아오기

    @Column(nullable = false)
    private Long user_id; // 사용자 정보 - 외래키로 받아오기

//    private String join; // 참여 상태

    private String role; // 역할
}
