package com.database_Design.Database_Design.service;

import com.database_Design.Database_Design.Repository.StudygrouppostRepository;
import com.database_Design.Database_Design.Repository.StudygroupRepository;
import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.Study_group_member;
import com.database_Design.Database_Design.entity.Study_group_post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class StudygrouppostService {

	private final StudygroupRepository studyGroupRepository;
	private final StudygrouppostRepository studyGroupPostRepository;

	@Transactional
	public void createOrUpdateNotice(Long std_id, Long user_id, String noticeContent) {
		// 1. 스터디 그룹 확인
		Study_group studyGroup = studyGroupRepository.findById(std_id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

		// 2. 요청자가 스터디장인지 확인
		validateLeaderPermission(studyGroup, user_id);

		// 3. 기존 공지사항 확인 및 처리
		Study_group_post notice = studyGroupPostRepository
				.findNotice(studyGroup, "공지사항")
				.orElseGet(() -> createNewNotice(studyGroup)); // 없으면 새로 생성

		// 공지사항 내용 업데이트
		updateNoticeContent(notice, noticeContent);
		studyGroupPostRepository.save(notice);
	}

	// 스터디장 권한 확인
	private void validateLeaderPermission(Study_group studyGroup, Long user_id) {
		if (!studyGroup.getStd_leader().getId().equals(user_id)) {
			throw new SecurityException("공지사항 작성 권한이 없습니다.");
		}
	}

	// 새 공지사항 생성
	private Study_group_post createNewNotice(Study_group studyGroup) {
		Study_group_post newNotice = new Study_group_post();
		newNotice.setStudyGroup(studyGroup);  // 스터디 그룹 설정
		newNotice.setGroupNotice("공지사항"); // 공지사항 타입 설정
		newNotice.setGroup_post_date(new Date()); // 작성 날짜 초기화
		return newNotice;
	}

	// 공지사항 내용 업데이트
	private void updateNoticeContent(Study_group_post notice, String noticeContent) {
		notice.setGroup_post_content(noticeContent); // 공지사항 내용 설정
		notice.setGroup_post_date(new Date());       // 수정 날짜 업데이트
	}



	@Transactional
	public void createGroupPost(Long std_id, Long member_id, String postContent) {
		// 1. 스터디 그룹 확인
		Study_group studyGroup = studyGroupRepository.findById(std_id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

		// 2. 스터디 멤버 여부 확인
		Study_group_member member = studyGroup.getStd_members().stream()
				.filter(m -> m.getUser().getId().equals(member_id)) // User의 ID와 비교
				.findFirst()
				.orElseThrow(() -> new SecurityException("스터디 멤버만 게시글을 작성할 수 있습니다."));

		// 3. 새로운 게시글 생성
		Study_group_post newPost = new Study_group_post();
		newPost.setStudyGroup(studyGroup); // 스터디 그룹 설정
		newPost.setGroup_post_writer(member); // 작성자 설정
		newPost.setGroup_post_content(postContent); // 게시글 내용 설정
		newPost.setGroup_post_date(new Date()); // 작성일 설정

		// 4. 저장
		studyGroupPostRepository.save(newPost);
	}

////	 게시글 작성 - 스터디 멤버라면 누구나 작성 가능.
//	public void createPost(Study_group_member member, Study_group group, Study_group_post post) {
//		if (!group.equals(member.getStudyGroup())) {
//			throw new RuntimeException("이 사용자는 해당 스터디의 멤버가 아닙니다.");
//		}
//		post.setGroup_post_writer(member);
//		post.setStudyGroup(group);
//		studyGroupPostRepository.save(post);
//	}
//	게시글 수정 및 삭제 - 작성자 본인 또는 스터디장만 수정/삭제 가능.
//	public void updatePost(Study_group_member member, Study_group_post post, String newContent) {
//		if (isAuthorizedToEdit(member, post)) {
//			post.setGroup_post_content(newContent);
//			studyGroupPostRepository.save(post);
//		} else {
//			throw new RuntimeException("수정 권한이 없습니다.");
//		}
//	}
//
//	public void deletePost(Study_group_member member, Study_group_post post) {
//		if (isAuthorizedToEdit(member, post)) {
//			studyGroupPostRepository.delete(post);
//		} else {
//			throw new RuntimeException("삭제 권한이 없습니다.");
//		}
//	}
//
//	private boolean isAuthorizedToEdit(Study_group_member member, Study_group_post post) {
//		return post.getWriter().equals(member) ||
//				(member.getRole().equalsIgnoreCase("Leader") &&
//						post.getStudyGroup().equals(member.getStudyGroup()));
//	}


	@Transactional
	public void updateGroupPost(Long post_id, Long user_id, String updatedContent) {
		// 1. 게시글 확인
		Study_group_post post = studyGroupPostRepository.findById(post_id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

		// 2. 수정 권한 확인 (작성자 또는 스터디장만 가능)
		if (!post.getGroup_post_writer().equals(user_id) &&
				!post.getStudyGroup().getStd_leader().getId().equals(user_id)) {
			throw new SecurityException("게시글 수정 권한이 없습니다.");
		}

		// 3. 게시글 내용 수정
		post.setGroup_post_content(updatedContent);
		studyGroupPostRepository.save(post);
	}

	@Transactional
	public void deleteGroupPost(Long post_id, Long user_id) {
		// 1. 게시글 확인
		Study_group_post post = studyGroupPostRepository.findById(post_id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

		// 2. 삭제 권한 확인 (작성자 또는 스터디장만 가능)
		if (!post.getGroup_post_writer().equals(user_id) &&
				!post.getStudyGroup().getStd_leader().getId().equals(user_id)) {
			throw new SecurityException("게시글 삭제 권한이 없습니다.");
		}

		// 3. 게시글 삭제
		studyGroupPostRepository.delete(post);
	}
}
