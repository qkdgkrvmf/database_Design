package com.database_Design.Database_Design.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

@Entity
public class Study_memory {
    @Id
    private Long memory_id;

    private String user_id;

    private Date std_start_time;

    private Date std_end_time;

    private Date std_date;

    private Long std_total;
}
