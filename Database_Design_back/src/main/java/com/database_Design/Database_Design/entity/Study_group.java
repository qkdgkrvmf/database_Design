package com.database_Design.Database_Design.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service

@Entity
public class Study_group {

    @Id
    private Long std_id;

    private Long std_leader;

    private String std_name;

    private int std_member_total;
}
