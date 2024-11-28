package com.database_Design.Database_Design.controller;

import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.service.StudygroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/studygroup")
@RequiredArgsConstructor
public class StudygroupController {

	private final StudygroupService studygroupService;

	/**
	 * 스터디 그룹 생성
	 *
	 * @param stdLeader       스터디장 ID
	 * @param stdName         스터디 이름
	 * @param stdDescription  스터디 설명
	 * @param stdRule         스터디 규칙
	 * @param notice          공지사항
	 * @param stdStartDate    시작 날짜
	 * @param stdEndDate      종료 날짜
	 * @return 생성된 스터디 그룹 정보
	 */
	@PostMapping("/create")
	public ResponseEntity<Study_group> createStudyGroup(
			@RequestParam String stdLeader, // Login_Id
			@RequestParam String stdName,
			@RequestParam String stdDescription, // 스터디 설명
			@RequestParam String stdRule,
			@RequestParam String notice,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date stdStartDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date stdEndDate) {

		Study_group createdGroup = studygroupService.createStudyGroup(stdLeader, stdName, stdDescription, stdRule, notice, stdStartDate, stdEndDate);
		return ResponseEntity.ok(createdGroup);
	}

	/**
	 * 스터디 그룹 가입
	 *
	 * @param stdId      스터디 그룹 ID
	 * @param stdLeader  가입 요청 사용자 ID
	 * @return 가입된 스터디 그룹 정보
	 */
	@PostMapping("/join")
	public ResponseEntity<Study_group> joinGroup(
			@RequestParam Long stdId, // 스터디 기본키
			@RequestParam String stdLeader) {

		Study_group joinedGroup = studygroupService.joinGroup(stdId, stdLeader);
		return ResponseEntity.ok(joinedGroup);
	}

	/**
	 * 스터디 그룹 탈퇴
	 *
	 * @param stdId      스터디 그룹 ID
	 * @param stdLeader  탈퇴 요청 사용자 ID
	 * @return 업데이트된 스터디 그룹 정보
	 */
	@PostMapping("/leave")
	public ResponseEntity<Study_group> leaveGroup(
			@RequestParam Long stdId,
			@RequestParam String stdLeader) {

		Study_group leftGroup = studygroupService.leaveGroup(stdId, stdLeader);
		return ResponseEntity.ok(leftGroup);
	}

	/**
	 * 스터디 그룹 종료
	 *
	 * @param stdId 스터디 그룹 ID
	 * @return 종료된 스터디 그룹 정보
	 */
	@PostMapping("/close")
	public ResponseEntity<Study_group> closeGroup(@RequestParam Long stdId) {
		Study_group closedGroup = studygroupService.closeGroup(stdId);
		return ResponseEntity.ok(closedGroup);
	}

	/**
	 * 모집 중인 스터디 그룹 조회
	 *
	 * @return 모집 중인 스터디 그룹 리스트
	 */
	@GetMapping("/recruiting")
	public ResponseEntity<List<Study_group>> getRecruitingStudyGroups() {
		List<Study_group> recruitingGroups = studygroupService.getRecruitingStudyGroups();
		return ResponseEntity.ok(recruitingGroups);
	}

	/**
	 * 특정 스터디 그룹의 세부 정보 조회
	 *
	 * @param stdId 스터디 그룹 ID
	 * @return 스터디 그룹 세부 정보 문자열
	 */
	@GetMapping("/{stdId}/details")
	public ResponseEntity<String> getStudyDetails(@PathVariable Long stdId) {
		String details = studygroupService.getStudyDetails(stdId);
		return ResponseEntity.ok(details);
	}
}
