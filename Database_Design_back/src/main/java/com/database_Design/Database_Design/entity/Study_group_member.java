package com.database_Design.Database_Design.entity;


import jakarta.persistence.Entity;

@Entity
public class Study_group_member {

    private Long member_id; // 스터디 그룹 식별자

    private Long std_id;; // 스터디 식별 기본키 - 외래키로 받아오기

    private Long user_id; // 사용자 정보 - 외래키로 받아오기

    private String join; // 참여 상태

    private String role; // 역할
}
