package com.database_Design.Database_Design.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Study_group_Study { // 스터디 그룹 학습량

    @Id // 기본키
    private Long study_group_id; // 스터디 id - 스터디그룹의 기본키

    private Date study_date;

    private Long total_study_time; // 그룹 전체 학습 시간
}
