package com.database_Design.Database_Design.controller;

import com.database_Design.Database_Design.Repository.UserRepository;
import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.User;
import com.database_Design.Database_Design.service.StudygroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studygroup")
@RequiredArgsConstructor
public class StudygroupController {

	private final StudygroupService studygroupService;
	private final UserRepository userRepository;

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
			//@RequestParam String notice,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date stdStartDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date stdEndDate) {

		//Study_group createdGroup = studygroupService.createStudyGroup(stdLeader, stdName, stdDescription, stdRule, notice, stdStartDate, stdEndDate);
		Study_group createdGroup = studygroupService.createStudyGroup(stdLeader, stdName, stdDescription, stdRule, stdStartDate, stdEndDate);
		return ResponseEntity.ok(createdGroup);
	}

	/**
	 * 스터디 그룹 가입
	 *
	 * @param stdId      스터디 그룹 ID
	 * @param loginId  가입 요청 사용자 ID
	 * @return 가입된 스터디 그룹 정보
	 */
	@PostMapping("/join") // 근데 중복 가입 에러남
	public ResponseEntity<Map<String, Object>> joinGroup(
			@RequestParam Long stdId, // 스터디 기본키
			@RequestParam String loginId) { // 가입하려는 사용자 LoginId

		Study_group joinedGroup = studygroupService.joinGroup(stdId, loginId);

		// 가입한 사용자 정보 찾기
		User user = userRepository.findByLoginId(loginId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		// 응답 구성
		Map<String, Object> response = new HashMap<>();
		response.put("studyGroup", joinedGroup);
		//response.put("user", user);

		return ResponseEntity.ok(response);
	}

	/**
	 * 스터디 그룹 탈퇴
	 *
	 * @param stdId      스터디 그룹 ID
	 * @param loginId  탈퇴 요청 사용자 ID
	 * @return 업데이트된 스터디 그룹 정보
	 */
	@PostMapping("/leave")
	public ResponseEntity<Map<String, Object>> leaveGroup(
			@RequestParam Long stdId,
			@RequestParam String loginId) {

		Study_group leftGroup = studygroupService.leaveGroup(stdId, loginId);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "탈퇴가 완료되었습니다.");
		response.put("studyGroup", leftGroup);

		return ResponseEntity.ok(response);
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

	/**
	 * 스터디 그룹 삭제
	 *
	 * @param stdId 스터디 그룹 ID
	 * @return 삭제 성공 메시지
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, String>> deleteGroup(@RequestParam Long stdId) {
		try {
			// 서비스 메서드를 호출하여 스터디 그룹 삭제
			studygroupService.deleteGroup(stdId);

			// 성공 메시지 반환
			Map<String, String> response = new HashMap<>();
			response.put("message", "스터디 그룹이 성공적으로 삭제되었습니다.");
			return ResponseEntity.ok(response);

		} catch (IllegalArgumentException ex) {
			// 존재하지 않는 스터디 그룹에 대한 처리
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
		} catch (Exception ex) {
			// 기타 서버 오류 처리
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "스터디 그룹 삭제 중 문제가 발생했습니다."));
		}
	}



}
