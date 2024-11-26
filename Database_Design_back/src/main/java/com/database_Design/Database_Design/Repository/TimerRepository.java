package com.database_Design.Database_Design.Repository;

import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.Timer;
import com.database_Design.Database_Design.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimerRepository extends JpaRepository<Timer, Long> {
    // 특정 사용자의 타이머 기록 조회
    List<Timer> findByUser(User user);

    // 특정 사용자의 특정 날짜 타이머 기록 조회
    List<Timer> findByUserAndTimerStartBetween(User user, LocalDateTime start, LocalDateTime end);

    // 특정 스터디 그룹의 타이머 기록 조회
    List<Timer> findByStudyGroup(Study_group studyGroup);

    // 특정 스터디 그룹의 특정 날짜 타이머 기록 조회
    List<Timer> findByStudyGroupAndTimerStartBetween(Study_group studyGroup, LocalDateTime start, LocalDateTime end);
}
