package com.database_Design.Database_Design.controller;

import com.database_Design.Database_Design.service.StudygrouppostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studygrouppost")
@RequiredArgsConstructor
public class StudygrouppostController {

	private final StudygrouppostService studygrouppostService;

	/**
	 * 공지사항 작성 또는 수정
	 *
	 * @param groupId         스터디 그룹 ID
	 * @param userId        작성자 ID (스터디장)
	 * @param newGroupRule 공지사항 내용
	 * @return 성공 메시지
	 */

	@PutMapping("/{groupId}/update-rule")
	public ResponseEntity<String> updateGroupRule(
			@PathVariable Long groupId,
			@RequestParam Long userId,
			@RequestParam String newGroupRule) {
		try {
			studygrouppostService.updateGroupRule(groupId, userId, newGroupRule);
			return ResponseEntity.ok("공지사항이 성공적으로 수정되었습니다.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	/**
	 * 스터디 그룹 게시글 작성
	 *
	 * @param groupId      스터디 그룹 ID
	 * @param userId     작성자 ID
	 * @param postContent 게시글 내용
	 * @return 성공 메시지
	 */
	@PostMapping("/{groupId}/post")
	public ResponseEntity<String> createGroupPost(
			@PathVariable Long groupId,
			@RequestParam Long userId,
			@RequestParam String postContent) {

		studygrouppostService.createGroupPost(groupId, userId, postContent);
		return ResponseEntity.ok("게시글이 성공적으로 작성되었습니다.");
	}

	/**
	 * 스터디 그룹 게시글 수정
	 *
	 * @param groupId        스터디 그룹 ID
	 * @param postId         게시글 ID
	 * @param userId         수정 요청자 ID
	 * @param updatedContent 수정할 게시글 내용
	 * @return 성공 메시지
	 */
	@PutMapping("/{groupId}/update-post")
	public ResponseEntity<String> updateGroupPost(
			@PathVariable Long groupId,
			//@PathVariable Long postId,
			@RequestParam Long postId,
			@RequestParam Long userId,
			@RequestParam String updatedContent) {

		// 서비스 호출 시 groupId를 전달
		studygrouppostService.updateGroupPost(groupId, postId, userId, updatedContent);
		return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다.");
	}


	/**
	 * 스터디 그룹 게시글 삭제
	 *
	 * @param postId 게시글 ID
	 * @param userId 삭제 요청자 ID
	 * @return 성공 메시지
	 */
	@DeleteMapping("/{groupId}/delete-post")
	public ResponseEntity<String> deleteGroupPost(
			@PathVariable Long groupId,
			@RequestParam Long postId,
			@RequestParam Long userId) {

		studygrouppostService.deleteGroupPost(groupId, postId, userId);
		return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
	}
}
