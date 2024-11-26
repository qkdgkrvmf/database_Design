package com.database_Design.Database_Design.service;

import com.database_Design.Database_Design.Repository.StudygrouppostRepository;
import com.database_Design.Database_Design.Repository.StudygroupRepository;
import com.database_Design.Database_Design.entity.Study_group;
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
		if (!studyGroup.getStd_leader().getId().equals(user_id)) {
			throw new SecurityException("공지사항 작성 권한이 없습니다.");
		}

		// 3. 기존 공지사항 확인
		Study_group_post existingNotice = studyGroupPostRepository.findById(std_id).orElse(null);

		if (existingNotice != null) {
			// 기존 공지사항이 있으면 내용 업데이트
			existingNotice.setGroup_notice(noticeContent);
			studyGroupPostRepository.save(existingNotice);
		} else {
			// 공지사항이 없으면 새로 생성
			Study_group_post newNotice = new Study_group_post();
			newNotice.setGroup_id(std_id); // 스터디 그룹 ID 설정
			newNotice.setGroup_notice(noticeContent); // 공지사항 내용 설정
			newNotice.setGroup_post_writer(studyGroup.getStd_leader().getId()); // 수정된 부분: Long 타입 설정
			newNotice.setGroup_post_date(new Date()); // 작성일 설정
			studyGroupPostRepository.save(newNotice);
		}
	}

	@Transactional
	public void createGroupPost(Long std_id, Long user_id, String postContent) {
		// 1. 스터디 그룹 확인
		Study_group studyGroup = studyGroupRepository.findById(std_id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

		// 2. 스터디 멤버 여부 확인 (작성 권한)
		boolean isMember = studyGroup.getStd_members().stream()
				.anyMatch(member -> member.getId().equals(user_id));

		if (!isMember) {
			throw new SecurityException("스터디 멤버만 게시글을 작성할 수 있습니다.");
		}

		// 3. 새로운 게시글 생성
		Study_group_post newPost = new Study_group_post();
		newPost.setStudyGroup(studyGroup); // 외래키로 스터디 그룹 설정
		newPost.setGroup_post_writer(user_id); // 게시글 작성자 설정
		newPost.setGroup_post_content(postContent); // 게시글 내용 설정
		newPost.setGroup_post_date(new Date()); // 작성일 설정

		// 4. 저장
		studyGroupPostRepository.save(newPost);
	}


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
