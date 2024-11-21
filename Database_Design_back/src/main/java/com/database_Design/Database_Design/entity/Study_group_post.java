package com.database_Design.Database_Design.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

@Entity
public class Study_group_post {

    @Id
    private Long post_id;

    private Long post_writer;

    private Date post_date;

    private String post_content;
}
