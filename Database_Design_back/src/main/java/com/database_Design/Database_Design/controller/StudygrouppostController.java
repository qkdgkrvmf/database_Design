package com.database_Design.Database_Design.controller;

import com.database_Design.Database_Design.service.StudygrouppostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studygroup/posts")
@RequiredArgsConstructor
public class StudygrouppostController {

	private final StudygrouppostService studygrouppostService;

	/**
	 * 공지사항 작성 또는 수정
	 *
	 * @param stdId         스터디 그룹 ID
	 * @param userId        작성자 ID (스터디장)
	 * @param noticeContent 공지사항 내용
	 * @return 성공 메시지
	 */
	@PostMapping("/{stdId}/notice")
	public ResponseEntity<String> createOrUpdateNotice(
			@PathVariable Long stdId,
			@RequestParam Long userId,
			@RequestParam String noticeContent) {

		studygrouppostService.createOrUpdateNotice(stdId, userId, noticeContent);
		return ResponseEntity.ok("공지사항이 성공적으로 작성/수정되었습니다.");
	}

	/**
	 * 스터디 그룹 게시글 작성
	 *
	 * @param stdId      스터디 그룹 ID
	 * @param userId     작성자 ID
	 * @param postContent 게시글 내용
	 * @return 성공 메시지
	 */
	@PostMapping("/{stdId}")
	public ResponseEntity<String> createGroupPost(
			@PathVariable Long stdId,
			@RequestParam Long userId,
			@RequestParam String postContent) {

		studygrouppostService.createGroupPost(stdId, userId, postContent);
		return ResponseEntity.ok("게시글이 성공적으로 작성되었습니다.");
	}

	/**
	 * 스터디 그룹 게시글 수정
	 *
	 * @param postId         게시글 ID
	 * @param userId         수정 요청자 ID
	 * @param updatedContent 수정할 게시글 내용
	 * @return 성공 메시지
	 */
	@PutMapping("/{postId}")
	public ResponseEntity<String> updateGroupPost(
			@PathVariable Long postId,
			@RequestParam Long userId,
			@RequestParam String updatedContent) {

		studygrouppostService.updateGroupPost(postId, userId, updatedContent);
		return ResponseEntity.ok("게시글이 성공적으로 수정되었습니다.");
	}

	/**
	 * 스터디 그룹 게시글 삭제
	 *
	 * @param postId 게시글 ID
	 * @param userId 삭제 요청자 ID
	 * @return 성공 메시지
	 */
	@DeleteMapping("/{postId}")
	public ResponseEntity<String> deleteGroupPost(
			@PathVariable Long postId,
			@RequestParam Long userId) {

		studygrouppostService.deleteGroupPost(postId, userId);
		return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
	}
}
