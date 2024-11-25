package com.database_Design.Database_Design.Repository;

import com.database_Design.Database_Design.entity.Study_goal;
import com.database_Design.Database_Design.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudygoalRepository extends JpaRepository<Study_goal,Long> {

    // 특정 사용자의 모든 할 일 조회
    List<Study_goal> findAllByUser(User user);
}
