package com.database_Design.Database_Design.service;

import com.database_Design.Database_Design.Repository.StudygroupRepository;
import com.database_Design.Database_Design.Repository.TimerRepository;
import com.database_Design.Database_Design.Repository.UserRepository;
import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.Timer;
import com.database_Design.Database_Design.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimerService {

    private final TimerRepository timerRepository;
    private final UserRepository userRepository;
    private final StudygroupRepository studyGroupRepository;

    public Long getUserTotalStudy(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Timer> timers = timerRepository.findByUser(user);

        return timers.stream()
                .mapToLong(Timer::getTimer_total)
                .sum(); // 총 학습량
    }

    public Long getUserDailyStudy(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Timer> timers = timerRepository.findByUserAndTimerStartBetween(user, startOfDay, endOfDay);

        return timers.stream()
                .mapToLong(Timer::getTimer_total)
                .sum(); // 일일 학습량
    }

    public Long getStudyTotalStudy(Long studyGroupId) {
        Study_group studyGroup = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Study group not found"));
        List<Timer> timers = timerRepository.findByStudyGroup(studyGroup);

        return timers.stream()
                .mapToLong(Timer::getTimer_total)
                .sum(); // 스터디 총 학습량
    }

    public Long getStudyDailyStudy(Long studyGroupId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        Study_group studyGroup = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Study group not found"));
        List<Timer> timers = timerRepository.findByStudyGroupAndTimerStartBetween(studyGroup, startOfDay, endOfDay);

        return timers.stream()
                .mapToLong(Timer::getTimer_total)
                .sum(); // 스터디 일일 학습량
    }
}
