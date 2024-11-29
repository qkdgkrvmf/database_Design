package com.database_Design.Database_Design.service;

import com.database_Design.Database_Design.Repository.StudygroupMemberRepository;
import com.database_Design.Database_Design.Repository.StudygroupRepository;
import com.database_Design.Database_Design.Repository.StudygrouppostRepository;
import com.database_Design.Database_Design.Repository.UserRepository;
import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.Study_group_member;
import com.database_Design.Database_Design.entity.Study_group_post;
import com.database_Design.Database_Design.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudygroupService {
    private final StudygroupRepository studyGroupRepository;
    private final UserRepository userRepository;
    private final StudygrouppostRepository studygrouppostRepository;
    private final StudygroupMemberRepository studyGroupMemberRepository; // Study_group_member Repository 추가

    // 그룹 가입
    @Transactional
    public Study_group joinGroup(Long std_id, String loginId) {
        Study_group studyGroup = studyGroupRepository.findById(std_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 이미 그룹에 가입되어 있는지 확인
        boolean alreadyJoined = studyGroupMemberRepository.findByStudyGroupAndUser(studyGroup, user).isPresent();
        if (alreadyJoined) {
            throw new IllegalStateException("사용자는 이미 스터디 그룹에 가입되어 있습니다."); // 예외 발생
        }

        // 새로운 멤버 추가
        Study_group_member newMember = new Study_group_member();
        newMember.setStudyGroup(studyGroup);
        newMember.setUser(user);
        newMember.setRole("스터디원"); // 기본 역할 설정
        studyGroupMemberRepository.save(newMember);

        // 멤버 수 증가
        studyGroup.setStd_member_total(studyGroup.getStd_member_total() + 1);

        return studyGroup;
    }

    // 그룹 탈퇴
    @Transactional // 트랜잭션 경계를 명시
    public Study_group leaveGroup(Long std_id, String loginId) {
        Study_group studyGroup = studyGroupRepository.findById(std_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

        if (studyGroup.getStd_member_total() <= 0) {
            throw new IllegalStateException("스터디 그룹에 멤버가 없습니다.");
        }

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 스터디 멤버 삭제
        studyGroupMemberRepository.deleteByStudyGroupAndUser(studyGroup, user);

        // 스터디 멤버 수 감소
        studyGroup.setStd_member_total(studyGroup.getStd_member_total() - 1);

        return studyGroupRepository.save(studyGroup);
    }

    // 스터디 생성
    public Study_group createStudyGroup(String std_leader, String std_name, String std_description,
                                        String std_rule, String notice, Date std_start_date, Date std_end_date) {
        User leader = userRepository.findByLoginId(std_leader)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 스터디 그룹 생성
        Study_group studyGroup = new Study_group();
        studyGroup.setStd_leader(leader);
        studyGroup.setStd_name(std_name);
        studyGroup.setStd_description(std_description);
        studyGroup.setGroup_rule(std_rule);
        studyGroup.setStd_start_date(std_start_date);
        studyGroup.setStd_end_date(std_end_date);
        studyGroup.setGroup_daily_std(0L);
        studyGroup.setStd_member_total(1L); // 리더 포함

        Study_group savedGroup = studyGroupRepository.save(studyGroup);

        // 스터디 멤버(스터디장) 추가
        Study_group_member leaderMember = new Study_group_member();
        leaderMember.setStudyGroup(savedGroup);
        leaderMember.setUser(leader);
        leaderMember.setRole("리더");
        studyGroupMemberRepository.save(leaderMember);

        return savedGroup;
    }

    // 모집 중인 스터디 조회
    public List<Study_group> getRecruitingStudyGroups() {
        return studyGroupRepository.findByStdstate(true); // true(모집중)인 스터디만 조회
    }

    // 선택한 스터디의 세부 정보 조회
    public String getStudyDetails(Long std_id) {
        // 스터디 정보 조회
        Study_group studyGroup = studyGroupRepository.findById(std_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스터디를 찾을 수 없습니다."));

//        // 게시판 규칙 조회
//        Study_group_post studyGroupPost = studygrouppostRepository.findById(std_id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 스터디의 게시판 규칙을 찾을 수 없습니다."));

        // 스터디 멤버 조회
        List<Study_group_member> members = studyGroupMemberRepository.findByStudyGroup(studyGroup);

        // 세부 정보 구성
        StringBuilder details = new StringBuilder();
        details.append("스터디 설명: ").append(studyGroup.getStd_description()).append("\n");
        details.append("스터디 규칙: ").append(studyGroup.getGroup_rule()).append("\n");
        // 멤버 리스트 추가
        details.append("멤버 리스트:\n");
        String memberDetails = members.stream()
                .map(member -> "- " + member.getUser().getName() + " (" + member.getRole() + ")")
                .collect(Collectors.joining("\n"));
        details.append(memberDetails);

        // 멤버 수는 실제 리스트 크기로 설정
        details.append("\n현재 멤버 수: ").append(members.size());

        return details.toString();
    }

    /**
     * 스터디 종료
     * @param std_id 스터디 ID
     * @return 종료된 스터디 그룹 정보
     */
    public Study_group closeGroup(Long std_id) {
        // 스터디 그룹 조회
        Study_group studyGroup = studyGroupRepository.findById(std_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

        // 스터디 종료 처리
        studyGroup.setStdstate(false); // false: 스터디 종료 상태
        studyGroup.setStd_end_date(new Date()); // 종료 날짜를 현재 시간으로 설정

        // 상태를 종료로 업데이트
        studyGroup.setStd_description(studyGroup.getStd_description() + " (스터디 종료)");

        // 변경된 스터디 그룹 저장
        return studyGroupRepository.save(studyGroup);
    }
}

