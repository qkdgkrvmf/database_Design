package com.database_Design.Database_Design.controller;


import com.database_Design.Database_Design.Repository.StudygroupRepository;
import com.database_Design.Database_Design.Repository.TimerRepository;
import com.database_Design.Database_Design.Repository.UserRepository;
import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.Timer;
import com.database_Design.Database_Design.entity.User;
import com.database_Design.Database_Design.security.Exception.ResourceNotFoundException;
import com.database_Design.Database_Design.service.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/timers")
@RequiredArgsConstructor
public class TimerController {
    private final TimerService timerService;
    private final UserRepository userRepository;
    private final StudygroupRepository studygroupRepository;
    private final TimerRepository timerRepository;

    @PostMapping("/add") // 타이머 생성
    public ResponseEntity<String> addTimer(@RequestBody Map<String, Object> timerDetails) {

        // 타이머 엔티티 생성 및 설정
        Timer timer = new Timer();
        timer.setTimer_title((String) timerDetails.get("timer_title"));
        timer.setTimer_content((String) timerDetails.get("timer_content"));
        timer.setTimerStart(LocalDateTime.parse((String) timerDetails.get("timer_start")));
        timer.setTimerEnd(LocalDateTime.parse((String) timerDetails.get("timer_end")));
        timer.setTimer_total(Long.valueOf((String) timerDetails.get("timer_total")));

        // 사용자 ID로 User 엔티티 설정 - 개인 타이머
        Long userId = Long.valueOf((String) timerDetails.get("user_id"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        timer.setUser(user);

        // 스터디 그룹 ID로 Study_group 엔티티 설정 - 스터디 타이머
        if (timerDetails.containsKey("study_group_id")) {
            Long studyGroupId = Long.valueOf((String) timerDetails.get("study_group_id"));
            Study_group studyGroup = studygroupRepository.findById(studyGroupId)
                    .orElseThrow(() -> new IllegalArgumentException("Study group not found"));
            timer.setStudyGroup(studyGroup);
        }

        // 타이머 저장
        timerRepository.save(timer);

        return ResponseEntity.ok("타이머 기록이 성공적으로 추가되었습니다!");
    }

    // 특정 사용자의 총 학습량 조회
    @GetMapping("/user/{userId}/total-study")
    public ResponseEntity<Map<String, Object>> getUserTotalStudy(@PathVariable Long userId) {
        Long totalStudy = timerService.getUserTotalStudy(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Map<String, Object> response = Map.of(
                "userId", userId,
                "userName", user.getName(),
                "totalStudyMinutes", totalStudy
        );

        return ResponseEntity.ok(response);
    }


    // 특정 사용자의 일일 학습량 조회
    @GetMapping("/user/{userId}/daily-study")
    public ResponseEntity<Long> getUserDailyStudy(@PathVariable Long userId) {
        Long dailyStudy = timerService.getUserDailyStudy(userId);
        return ResponseEntity.ok(dailyStudy);
    }

    // 특정 스터디 그룹의 총 학습량 조회
    @GetMapping("/study-group/{studyGroupId}/total-study")
    public ResponseEntity<Long> getStudyTotalStudy(@PathVariable Long studyGroupId) {
        Long totalStudy = timerService.getStudyTotalStudy(studyGroupId);
        return ResponseEntity.ok(totalStudy);
    }

    // 특정 스터디 그룹의 일일 학습량 조회
    @GetMapping("/study-group/{studyGroupId}/daily-study")
    public ResponseEntity<Long> getStudyDailyStudy(@PathVariable Long studyGroupId) {
        Long dailyStudy = timerService.getStudyDailyStudy(studyGroupId);
        return ResponseEntity.ok(dailyStudy);
    }

    // 타이머 수정 - 타이머 내용,제목 수정 가능하게 하려고...
    @PutMapping("/{timerId}")
    public ResponseEntity<String> updateTimer(@PathVariable Long timerId, @RequestBody Map<String, Object> timerDetails) {
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new ResourceNotFoundException("Timer not found"));

        if (timerDetails.containsKey("timer_title")) { // title 수정
            timer.setTimer_title((String) timerDetails.get("timer_title"));
        }
        if (timerDetails.containsKey("timer_content")) { // content 수정
            timer.setTimer_content((String) timerDetails.get("timer_content"));
        }

        timerRepository.save(timer);
        return ResponseEntity.ok("타이머가 성공적으로 업데이트되었습니다!");
    }


    // 타이머 삭제
    @DeleteMapping("/{timerId}")
    public ResponseEntity<String> deleteTimer(@PathVariable Long timerId) {
        // 타이머 ID로 Timer 조회
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new IllegalArgumentException("Timer not found"));

        // 타이머 삭제
        timerRepository.delete(timer);

        return ResponseEntity.ok("타이머 기록이 성공적으로 삭제되었습니다!");
    }

}
