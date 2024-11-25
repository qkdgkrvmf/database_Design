//package com.database_Design.Database_Design.service;
//
//import com.database_Design.Database_Design.Repository.StudygrouppostRepository;
//import com.database_Design.Database_Design.Repository.StudyMemoryRepository;
//import com.database_Design.Database_Design.entity.Studygrouppost;
//import com.database_Design.Database_Design.entity.StudyMemory;
//import com.database_Design.Database_Design.entity.Studygroup;
//import com.database_Design.Database_Design.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class StudygrouppostService {
//
//	// 게시글 저장소와 연동
//	private final StudygrouppostRepository studygrouppostRepository;
//
//	// 스터디 그룹 서비스 (그룹 관련 기능 제공)
//	private final StudygroupService studygroupService;
//
//	// 사용자 서비스 (회원 정보 관리 기능 제공)
//	private final UserService userService;
//
//	/**
//	 * 공지사항 등록
//	 * 그룹장만 공지사항을 작성할 수 있도록 제한
//	 *
//	 * @param group_id 스터디 그룹 ID
//	 * @param user_id  작성자 ID
//	 * @param post_content  게시글 내용
//	 * @return 작성된 Studygrouppost 객체
//	 */
//	public Studygrouppost createNotice(Long group_id, Long user_id, String post_content) {
//		// 스터디 그룹 가져오기
//		Studygroup studygroup = studygroupService.getStudygroupById(group_id);
//
//		// 그룹장 여부 확인
//		if (!studygroup.getStdLeader().equals(user_id)) {
//			throw new IllegalArgumentException("공지사항은 그룹장만 작성할 수 있습니다.");
//		}
//
//		// 공지사항 생성
//		Studygrouppost notice = Studygrouppost.builder()
//				.postWriter(user_id)
//				.groupId(group_id)
//				.postContent(post_content)
//				.isNotice(true) // 공지사항 여부 플래그 설정
//				.build();
//
//		return studygrouppostRepository.save(notice); // DB에 저장
//	}
//
//	/**
//	 * 공지사항 수정
//	 * 그룹장만 공지사항 수정 가능
//	 *
//	 * @param post_id    게시글 ID
//	 * @param user_id    수정 요청한 사용자 ID
//	 * @param newContent 수정할 내용
//	 * @return 수정된 Studygrouppost 객체
//	 */
//	public Studygrouppost updateNotice(Long post_id, Long user_id, String newContent) {
//		// 게시글 가져오기
//		Studygrouppost post = studygrouppostRepository.findById(post_id)
//				.orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
//
//		// 그룹 정보 확인
//		Studygroup studygroup = studygroupService.getStudygroupById(post.getGroupId());
//
//		// 그룹장 여부 확인
//		if (!studygroup.getStdLeader().equals(user_id)) {
//			throw new IllegalArgumentException("공지사항 수정 권한이 없습니다.");
//		}
//
//		// 게시글 내용 수정
//		post.setPostContent(newContent);
//		return studygrouppostRepository.save(post); // 수정된 게시글 저장
//	}
//
//	/**
//	 * 공용 게시글 작성
//	 * 그룹의 멤버만 게시글 작성 가능
//	 *
//	 * @param group_id 스터디 그룹 ID
//	 * @param user_id  작성자 ID
//	 * @param post_content  게시글 내용
//	 * @return 작성된 Studygrouppost 객체
//	 */
//	public Studygrouppost createPost(Long group_id, Long user_id, String post_content) {
//		// 스터디 그룹 확인
//		Studygroup studygroup = studygroupService.getStudygroupById(group_id);
//
//		// 그룹 멤버 여부 확인
//		if (!studygroupService.isGroupMember(group_id, user_id)) {
//			throw new IllegalArgumentException("스터디 그룹 멤버만 게시글을 작성할 수 있습니다.");
//		}
//
//		// 게시글 생성
//		Studygrouppost post = Studygrouppost.builder()
//				.postWriter(user_id)
//				.groupId(group_id)
//				.postContent(post_content)
//				.isNotice(false) // 공용 게시글 여부 설정
//				.build();
//
//		return studygrouppostRepository.save(post); // DB에 저장
//	}
//
//	/**
//	 * 공용 게시글 수정
//	 * 작성자 또는 그룹장만 수정 가능
//	 *
//	 * @param post_id    게시글 ID
//	 * @param user_id    수정 요청한 사용자 ID
//	 * @param newContent 수정할 내용
//	 * @return 수정된 Studygrouppost 객체
//	 */
//	public Studygrouppost updatePost(Long post_id, Long user_id, String newContent) {
//		// 게시글 가져오기
//		Studygrouppost post = studygrouppostRepository.findById(post_id)
//				.orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
//
//		// 그룹 정보 확인
//		Studygroup studygroup = studygroupService.getStudygroupById(post.getGroupId());
//
//		// 작성자 또는 그룹장 여부 확인
//		if (!post.getPostWriter().equals(user_id) && !studygroup.getStdLeader().equals(user_id)) {
//			throw new IllegalArgumentException("게시글 수정 권한이 없습니다.");
//		}
//
//		// 게시글 내용 수정
//		post.setPostContent(newContent);
//		return studygrouppostRepository.save(post); // 수정된 게시글 저장
//	}
//
//	/**
//	 * 스터디 그룹 내 멤버 공부 순위 조회
//	 * 하루 공부량 기준으로 정렬 후 상위 5명 반환
//	 *
//	 * @param group_id 스터디 그룹 ID
//	 * @return 상위 5명의 사용자 리스트
//	 */
//	public List<User> getStudyRanking(Long group_id) {
//		// 그룹 멤버 가져오기
//		List<User> members = studygroupService.getGroupMembers(group_id);
//
//		// 공부량 기준 내림차순 정렬 (하루 공부량 필드: dailyStudyTime)
//		return members.stream()
//				.sorted((u1, u2) -> Long.compare(u2.getDailyStudyTime(), u1.getDailyStudyTime())) // 내림차순 정렬
//				.limit(5) // 상위 5명만 반환
//				.collect(Collectors.toList());
//	}
//}
