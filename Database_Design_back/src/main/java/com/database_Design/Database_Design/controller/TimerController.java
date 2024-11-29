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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

@RestController
@RequestMapping("/timers")
@RequiredArgsConstructor
public class TimerController {
    private final TimerService timerService;
    private final UserRepository userRepository;
    private final StudygroupRepository studygroupRepository;
    private final TimerRepository timerRepository;

    @PostMapping("/add") // 새로운 타이머 생성(타이머 이름, 내용, 사용자 정보)
    public ResponseEntity<String> addTimer(
            @RequestParam String timer_title,
            @RequestParam String timer_content,
            @RequestParam Long user_id, // 유저의 기본키?
            @RequestParam(required = false) Long study_group_id // 선택적 매개변수
    ) {
        try {
            // 타이머 엔티티 생성 및 설정
            Timer timer = new Timer();
            timer.setTimer_title(timer_title);
            timer.setTimer_content(timer_content);
            timer.setTimer_total(0L); // 타이머 처음 생성 시 0으로 초기화

            // 사용자 ID로 User 엔티티 설정
            User user = userRepository.findById(user_id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            timer.setUser(user);

            // 스터디 그룹 ID로 Study_group 엔티티 설정 (선택적)
            if (study_group_id != null) {
                Study_group studyGroup = studygroupRepository.findById(study_group_id)
                        .orElseThrow(() -> new IllegalArgumentException("Study group not found"));
                timer.setStudyGroup(studyGroup);
            }

            // 타이머 저장
            timerRepository.save(timer);

            return ResponseEntity.ok("타이머 기록이 성공적으로 추가되었습니다!");

        } catch (IllegalArgumentException e) {
            // 클라이언트 측 문제 처리
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 기타 서버 오류 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버에서 문제가 발생했습니다.");
        }
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

    // 타이머 시작
    @PostMapping("/start")
    public ResponseEntity<Timer> startTimer(
            @RequestParam Long user_id,
            @RequestParam(required = false) Long study_group_id,
            @RequestParam String timer_title,
            @RequestParam String timer_content
    ) {
        try {
            Timer timer = timerService.startTimer(user_id, study_group_id, timer_title, timer_content);
            return ResponseEntity.ok(timer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 타이머 종료
    @PostMapping("/stop/{timerId}")
    public ResponseEntity<Timer> stopTimer(@PathVariable Long timerId) {
        Timer timer = timerService.stopTimer(timerId);
        return ResponseEntity.ok(timer);
    }

    // 타이머 일시 정지
    @PostMapping("/pause/{timerId}")
    public ResponseEntity<Timer> pauseTimer(@PathVariable Long timerId) {
        Timer timer = timerService.pauseTimer(timerId);
        return ResponseEntity.ok(timer);
    }

    // 타이머 재시작
    @PostMapping("/resume/{timerId}")
    public ResponseEntity<Timer> resumeTimer(@PathVariable Long timerId) {
        Timer timer = timerService.resumeTimer(timerId);
        return ResponseEntity.ok(timer);
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
    @PutMapping("/update/{timerId}")
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
    @DeleteMapping("/delete/{timerId}")
    public ResponseEntity<String> deleteTimer(@PathVariable Long timerId) {
        // 타이머 ID로 Timer 조회
        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new IllegalArgumentException("Timer not found"));

        // 타이머 삭제
        timerRepository.delete(timer);

        return ResponseEntity.ok("타이머 기록이 성공적으로 삭제되었습니다!");
    }

}
