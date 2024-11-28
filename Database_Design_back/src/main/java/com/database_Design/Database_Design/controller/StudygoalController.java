package com.database_Design.Database_Design.controller;

import com.database_Design.Database_Design.entity.Study_goal;
import com.database_Design.Database_Design.service.StudygoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/studygoal")
@RequiredArgsConstructor
public class StudygoalController {

	private final StudygoalService studygoalService;

	/**
	 * 새로운 목표 추가
	 *
	 * @param userId            사용자 ID
	 * @param content           목표 내용
	 * @param startDate         목표 시작 날짜
	 * @param endDate           목표 종료 날짜
	 * @return 생성된 Study_goal 객체
	 */
	@PostMapping("/add")
	public ResponseEntity<Study_goal> addGoal(
			@RequestParam String userId,
			@RequestParam String content,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

		Study_goal newGoal = studygoalService.addGoal(userId, content, startDate, endDate);
		return ResponseEntity.ok(newGoal);
	}

	/**
	 * 목표 수정
	 *
	 * @param goalId   목표 ID
	 * @param content  수정할 목표 내용
	 * @param startDate 수정할 시작 날짜
	 * @param endDate   수정할 종료 날짜
	 * @return 수정된 Study_goal 객체
	 */
	@PutMapping("/update/{goalId}")
	public ResponseEntity<Study_goal> updateGoal(
			@PathVariable Long goalId,
			@RequestParam String content,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, // 문자열을 Date로 변환
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate) {

		Study_goal updatedGoal = studygoalService.updateGoal(goalId, content, startDate, endDate);
		return ResponseEntity.ok(updatedGoal);
	}

	/**
	 * 목표 삭제
	 *
	 * @param goalId 목표 ID
	 * @return 성공 메시지
	 */
	@DeleteMapping("/delete/{goalId}")
	public ResponseEntity<String> deleteGoal(@PathVariable Long goalId) {
		studygoalService.deleteGoal(goalId);
		return ResponseEntity.ok("목표가 성공적으로 삭제되었습니다.");
	}

	/**
	 * 목표 완료 처리
	 *
	 * @param goalId 목표 ID
	 * @return 완료 처리된 Study_goal 객체
	 */
	@PutMapping("/{goalId}/complete")
	public ResponseEntity<Study_goal> markGoalAsCompleted(@PathVariable Long goalId) {
		Study_goal completedGoal = studygoalService.markGoalAsCompleted(goalId);
		return ResponseEntity.ok(completedGoal);
	}

	/**
	 * 사용자별 목표 목록 조회
	 *
	 * @param userId 사용자 ID
	 * @return 사용자의 목표 목록
	 */
	@GetMapping("/{userId}/list")
	public ResponseEntity<?> getGoalsByUser(@PathVariable String userId) { // 아이디(Login_id - String 타입)로 리스트 조회
		List<Study_goal> userGoals = studygoalService.getGoalsByUser(userId);
		if (userGoals.isEmpty()) {
			return ResponseEntity.ok("사용자의 목표가 없습니다.");
		}
		return ResponseEntity.ok(userGoals);
	}

}
