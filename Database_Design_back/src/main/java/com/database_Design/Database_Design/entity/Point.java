package com.database_Design.Database_Design.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Point {

    @Id
    private Long grade_id;

    private Long user_id;

    private String grade_name;

    private Long point;
}
