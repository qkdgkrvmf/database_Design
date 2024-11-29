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


    // 타이머 시작
    // 타이머 시작 (기존 타이머를 재사용하거나 새로 생성)
    public Timer startTimer(Long userId, Long studyGroupId, String title, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 실행 중인 타이머가 있는지 확인
        List<Timer> activeTimers = timerRepository.findByUserAndStatus(user, Timer.TimerStatus.STARTED);
        if (!activeTimers.isEmpty()) {
            // 실행 중인 타이머가 있으면 예외를 던지거나 상태를 변경 (선택에 따라 동작 설정)
            Timer activeTimer = activeTimers.get(0); // 첫 번째 실행 중인 타이머
            activeTimer.setTimerStart(LocalDateTime.now()); // 시작 시간을 갱신
            activeTimer.setStatus(Timer.TimerStatus.STARTED);
            return timerRepository.save(activeTimer);
        }

        // 실행 중인 타이머가 없으면 새로 생성
        Timer timer = new Timer();
        timer.setTimer_title(title);
        timer.setTimer_content(content);
        timer.setTimerStart(LocalDateTime.now());
        timer.setTimer_total(0L); // 초기값
        timer.setStatus(Timer.TimerStatus.STARTED);
        timer.setUser(user);

        if (studyGroupId != null) {
            Study_group studyGroup = studyGroupRepository.findById(studyGroupId)
                    .orElseThrow(() -> new IllegalArgumentException("Study group not found"));
            timer.setStudyGroup(studyGroup);
        }

        return timerRepository.save(timer);
    }

    // 타이머 종료
    public Timer stopTimer(Long timerId) {
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new IllegalArgumentException("Timer not found"));

        if (timer.getStatus() != Timer.TimerStatus.STARTED) {
            throw new IllegalStateException("Timer is not running.");
        }

        timer.setTimerEnd(LocalDateTime.now());
        long totalSeconds = java.time.Duration.between(timer.getTimerStart(), timer.getTimerEnd()).getSeconds();
        timer.setTimer_total(totalSeconds);
        timer.setStatus(Timer.TimerStatus.STOPPED);

        return timerRepository.save(timer);
    }

    // 타이머 일시 정지
    public Timer pauseTimer(Long timerId) {
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new IllegalArgumentException("Timer not found"));

        if (timer.getStatus() != Timer.TimerStatus.STARTED) {
            throw new IllegalStateException("Timer is not running.");
        }

        timer.setTimerEnd(LocalDateTime.now());
        long elapsedSeconds = java.time.Duration.between(timer.getTimerStart(), timer.getTimerEnd()).getSeconds();
        timer.setTimer_total(timer.getTimer_total() + elapsedSeconds);
        timer.setStatus(Timer.TimerStatus.PAUSED);

        return timerRepository.save(timer);
    }

    // 타이머 재시작
    public Timer resumeTimer(Long timerId) {
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new IllegalArgumentException("Timer not found"));

        if (timer.getStatus() != Timer.TimerStatus.PAUSED) {
            throw new IllegalStateException("Timer is not paused.");
        }

        timer.setTimerStart(LocalDateTime.now());
        timer.setStatus(Timer.TimerStatus.STARTED);

        return timerRepository.save(timer);
    }

}
