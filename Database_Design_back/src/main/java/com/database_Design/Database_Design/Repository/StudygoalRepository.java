package com.database_Design.Database_Design.Repository;

import com.database_Design.Database_Design.entity.Study_goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudygoalRepository extends JpaRepository<Study_goal,Long> {
}
