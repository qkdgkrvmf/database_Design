package com.database_Design.Database_Design.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Study_group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long std_id; // 스터디 그룹의 고유 식별자

    @JsonIgnore // 직렬화에서 제외
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "std_leader", nullable = false)
    private User std_leader;

    @JsonIgnore // 직렬화에서 제외
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_group_post> posts = new ArrayList<>();

    @JsonIgnore // 직렬화에서 제외
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Study_group_member> std_members = new ArrayList<>();

    @JsonIgnore // 직렬화에서 제외
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timer> timers = new ArrayList<>();

    @JsonIgnore // 직렬화에서 제외
    private String std_name;

    @JsonIgnore // 직렬화에서 제외
    private String std_description;

    @JsonIgnore // 직렬화에서 제외
    private Long member_daily_std;

    @JsonIgnore // 직렬화에서 제외
    private Long group_daily_std;

    @JsonIgnore // 직렬화에서 제외
    private Long std_member_total;

    @JsonIgnore // 직렬화에서 제외
    private Date std_start_date;

    @JsonIgnore // 직렬화에서 제외
    private Date std_end_date;

    @JsonIgnore // 직렬화에서 제외
    private String group_rule;

    @JsonIgnore // 직렬화에서 제외
    @Column(name = "stdstate")
    private Boolean stdstate = true;

    public Long getstd_Id() {
        return std_id;
    }
}
