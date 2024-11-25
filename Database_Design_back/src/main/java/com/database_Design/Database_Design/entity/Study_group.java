package com.database_Design.Database_Design.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Service
@Entity
@NoArgsConstructor
public class Study_group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long std_id;

    @ManyToOne(fetch = FetchType.LAZY) // 스터디장(User와의 관계)
    @JoinColumn(name = "std_leader_id", nullable = false)
    private User std_leader;

    private String std_name;

    private int std_member_total;

    private Date std_start_date; // LocalDateTime 사용

    private Date std_end_date; // LocalDateTime 사용
}
