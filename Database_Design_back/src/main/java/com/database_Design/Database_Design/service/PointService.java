package com.database_Design.Database_Design.service;

import com.database_Design.Database_Design.Repository.PointRepository;
import com.database_Design.Database_Design.Repository.StudyMemoryRepository;
import com.database_Design.Database_Design.entity.Point;
import com.database_Design.Database_Design.entity.StudyMemory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

	private final PointRepository pointRepository; // 포인트 데이터를 관리하는 리포지토리
	private final StudyMemoryRepository studyMemoryRepository; // 공부 기록 데이터를 관리하는 리포지토리

	/**
	 * 일일 공부 시간에 따라 포인트를 계산하고 부여
	 *
	 * @param userId 유저 ID
	 * @param groupId 스터디 그룹 ID
	 */
	public void calculateAndAddPoints(Long userId, Long groupId) {
		// 사용자 개인의 일일 공부 기록 가져오기
		List<StudyMemory> personalRecords = studyMemoryRepository.findByUserId(userId);

		// 개인 총 공부 시간(초 단위) 계산
		long personalStudyTime = personalRecords.stream()
				.mapToLong(record -> Duration.between(record.getStdStartTime(), record.getStdEndTime()).toSeconds())
				.sum();

		// 10분(600초)마다 1포인트 부여
		long personalPoints = personalStudyTime / 600;

		// 포인트 기록 가져오기 또는 생성
		Point point = pointRepository.findByUserId(userId)
				.orElseGet(() -> Point.builder()
						.userId(userId)
						.gradeName("프라이") // 초기 등급 설정
						.point(0L)
						.build()
				);

		// 기존 포인트에 개인 공부 포인트 추가
		point.setPoint(point.getPoint() + personalPoints);

		// 등급 갱신
		point.setGradeName(calculateGrade(point.getPoint()));

		// 업데이트된 포인트 저장
		pointRepository.save(point);
	}

	/**
	 * 총 포인트에 따라 등급 계산
	 *
	 * @param totalPoints 총 포인트
	 * @return 새로운 등급 이름
	 */
	private String calculateGrade(long totalPoints) {
		if (totalPoints >= 500) {
			return "닭";
		} else if (totalPoints >= 360) {
			return "병아리";
		} else if (totalPoints >= 270) {
			return "깨진 알";
		} else if (totalPoints >= 180) {
			return "금이 간 알";
		} else if (totalPoints >= 90) {
			return "알";
		} else {
			return "프라이";
		}
	}

	/**
	 * 특정 유저의 현재 포인트와 등급 조회
	 *
	 * @param userId 유저 ID
	 * @return 포인트와 등급 정보
	 */
	public Point getUserPoint(Long userId) {
		return pointRepository.findByUserId(userId)
				.orElseThrow(() -> new IllegalArgumentException("해당 유저의 포인트 정보를 찾을 수 없습니다."));
	}

	/**
	 * 스터디 그룹 전체 멤버의 총 공부 시간을 기반으로 포인트 추가
	 *
	 * @param groupId 스터디 그룹 ID
	 */
	public void addGroupStudyPoints(Long groupId) {
		// 스터디 그룹의 모든 멤버의 공부 기록 가져오기
		List<StudyMemory> groupRecords = studyMemoryRepository.findByGroupId(groupId);

		// 그룹 멤버별 공부 시간(초 단위) 합산
		groupRecords.stream()
				.collect(Collectors.groupingBy(
						StudyMemory::getUserId, // 유저 ID를 기준으로 그룹화
						Collectors.summingLong(record -> Duration.between(
								record.getStdStartTime(),
								record.getStdEndTime()
						).toSeconds())
				))
				.forEach((userId, totalTime) -> {
					// 각 사용자에게 포인트 추가
					long groupPoints = totalTime / 600; // 10분당 1포인트
					Point point = pointRepository.findByUserId(userId)
							.orElseGet(() -> Point.builder()
									.userId(userId)
									.gradeName("프라이")
									.point(0L)
									.build()
							);

					// 포인트 갱신
					point.setPoint(point.getPoint() + groupPoints);
					point.setGradeName(calculateGrade(point.getPoint()));
					pointRepository.save(point);
				});
	}
}
