package com.database_Design.Database_Design.service;

import com.database_Design.Database_Design.Repository.StudyMemoryRepository;
import com.database_Design.Database_Design.entity.StudyMemory;
import com.database_Design.Database_Design.entity.Studygroup;
import com.database_Design.Database_Design.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyMemoryService {

	private final StudyMemoryRepository studyMemoryRepository; // StudyMemory 데이터를 관리하는 리포지토리
	private final StudygroupService studygroupService; // 스터디 그룹 관련 서비스
	private final UserService userService; // 사용자 관련 서비스

	/**
	 * 공부량 기록 시작
	 * 특정 사용자가 공부를 시작하며, 과목 및 시작 시간을 기록
	 *
	 * @param userId     사용자 ID
	 * @param groupId    스터디 그룹 ID
	 * @param subject    공부 과목
	 * @return 시작된 StudyMemory 객체
	 */
	public StudyMemory startStudy(Long userId, Long groupId, String subject) {
		// 사용자와 그룹 검증
		userService.getUserInfo(userId); // 유효한 사용자 여부 확인
		studygroupService.validateGroupMembership(groupId, userId); // 그룹 멤버 여부 확인

		// 새로운 공부 기록 생성
		StudyMemory studyMemory = StudyMemory.builder()
				.userId(userId)
				.groupId(groupId)
				.stdStartTime(LocalDateTime.now()) // 공부 시작 시간
				.stdDate(LocalDateTime.now().toLocalDate()) // 자정이 넘어도 시작한 날짜로 기록
				.subject(subject) // 과목 저장
				.build();

		return studyMemoryRepository.save(studyMemory); // DB에 저장
	}

	/**
	 * 공부량 기록 종료
	 * 특정 사용자의 현재 진행 중인 공부 기록 종료 및 저장
	 *
	 * @param memoryId 공부 기록 ID
	 * @return 종료된 StudyMemory 객체
	 */
	public StudyMemory stopStudy(Long memoryId) {
		// 공부 기록 가져오기
		StudyMemory studyMemory = studyMemoryRepository.findById(memoryId)
				.orElseThrow(() -> new IllegalArgumentException("진행 중인 공부 기록을 찾을 수 없습니다."));

		// 공부 종료 시간 기록
		studyMemory.setStdEndTime(LocalDateTime.now());

		// 총 공부 시간 계산 (초 단위)
		long totalStudyTime = java.time.Duration.between(
				studyMemory.getStdStartTime(),
				studyMemory.getStdEndTime()
		).getSeconds();

		studyMemory.setStdTotal(totalStudyTime); // 총 공부 시간 설정

		return studyMemoryRepository.save(studyMemory); // DB에 저장
	}

	/**
	 * 스터디 그룹의 총 공부 시간 계산
	 * 그룹 멤버들의 일일 공부 시간을 합산하여 총 공부 시간을 반환
	 *
	 * @param groupId 스터디 그룹 ID
	 * @return 스터디 그룹 총 공부 시간 (초 단위)
	 */
	public long getTotalStudyTime(Long groupId) {
		// 그룹 멤버 가져오기
		List<User> groupMembers = studygroupService.getGroupMembers(groupId);

		// 멤버 ID 리스트 추출
		List<Long> memberIds = groupMembers.stream()
				.map(User::getUserId)
				.toList();

		// 멤버들의 모든 공부 기록 가져오기
		List<StudyMemory> studyMemories = studyMemoryRepository.findByUserIdIn(memberIds);

		// 총 공부 시간 합산
		return studyMemories.stream()
				.mapToLong(StudyMemory::getStdTotal)
				.sum();
	}
}
