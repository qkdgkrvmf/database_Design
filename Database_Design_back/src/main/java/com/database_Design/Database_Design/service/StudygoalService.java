package com.database_Design.Database_Design.service;

import com.database_Design.Database_Design.Repository.StudygoalRepository;
import com.database_Design.Database_Design.Repository.UserRepository;
import com.database_Design.Database_Design.entity.Study_goal;
import com.database_Design.Database_Design.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudygoalService {

    private final StudygoalRepository studyGoalRepository;
    private final UserRepository userRepository;

    // 할 일 추가
    public Study_goal addGoal(String user_id, String content, Date std_goal_start_date, Date std_goal_end_date) {
        // User 조회
        User user = userRepository.findByLoginId(user_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 새 목표 생성
        Study_goal goal = Study_goal.builder()
                .content(content)
                .std_goal_start_date(std_goal_start_date)
                .std_goal_end_date(std_goal_end_date)
                .completed(false) // 기본값 설정
                .user_id(user)
                .build();

        return studyGoalRepository.save(goal);
    }

    // 할 일 수정
    public Study_goal updateGoal(Long goalId, String content, Date startDate, Date endDate) {
        Study_goal goal = studyGoalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 목표입니다."));

        goal.setContent(content);
        goal.setStd_goal_start_date(startDate);
        goal.setStd_goal_end_date(endDate);

        return studyGoalRepository.save(goal);
    }

    // 할 일 삭제
    public void deleteGoal(Long goalId) {
        Study_goal goal = studyGoalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 목표입니다."));

        studyGoalRepository.delete(goal);
    }

    // 할 일 완료 처리
    public Study_goal markGoalAsCompleted(Long goalId) {
        Study_goal goal = studyGoalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 목표입니다."));

        goal.setCompleted(true);

        return studyGoalRepository.save(goal);
    }

    // 사용자별 할 일 목록 조회
    public List<Study_goal> getGoalsByUser(String userId) {
        User user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return studyGoalRepository.findAllByUser(user);
    }
}