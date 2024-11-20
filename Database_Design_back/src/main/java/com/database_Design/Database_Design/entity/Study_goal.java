package com.database_Design.Database_Design.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

@Entity
public class Study_goal {

    @Id
    private Long goal_id;

    private Long user_id;

    private Long std_id;

    private Date std_goal_start_date;

    private Date std_goal_end_date;

    private String std_goal_archive;
}
