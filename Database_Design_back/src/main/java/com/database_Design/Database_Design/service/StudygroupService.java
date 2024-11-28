package com.database_Design.Database_Design.service;

import com.database_Design.Database_Design.Repository.StudygroupRepository;
import com.database_Design.Database_Design.Repository.StudygrouppostRepository;
import com.database_Design.Database_Design.Repository.UserRepository;
import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.Study_group_post;
import com.database_Design.Database_Design.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudygroupService {
    private final StudygroupRepository studyGroupRepository;
    private final UserRepository userRepository;
    private final StudygrouppostRepository studygrouppostRepository;


    // 그룹 가입
    public Study_group joinGroup(Long std_id, String std_leader) {
        Study_group studyGroup = studyGroupRepository.findById(std_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

        if (studyGroup.getStd_member_total() >= 10) { // 최대 멤버 제한
            throw new IllegalStateException("스터디 그룹 정원이 가득 찼습니다.");
        }

        User user = userRepository.findByLoginId(std_leader)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        studyGroup.setStd_member_total(studyGroup.getStd_member_total() + 1);

        return studyGroupRepository.save(studyGroup);
    }

    // 그룹 탈퇴
    public Study_group leaveGroup(Long std_id, String std_leader) {
        Study_group studyGroup = studyGroupRepository.findById(std_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

        if (studyGroup.getStd_member_total() <= 0) {
            throw new IllegalStateException("스터디 그룹에 멤버가 없습니다.");
        }

        User user = userRepository.findByLoginId(std_leader) // std_leader = User의 login_id
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        studyGroup.setStd_member_total(studyGroup.getStd_member_total() - 1);

        return studyGroupRepository.save(studyGroup);
    }

    // 그룹 종료
    public Study_group closeGroup(Long std_id) {
        Study_group studyGroup = studyGroupRepository.findById(std_id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        studyGroup.setStd_end_date(date);

        return studyGroupRepository.save(studyGroup);
    }

    // 스터디 생성
    public Study_group createStudyGroup(String std_leader, String std_name, String std_description,String std_rule, String notice, Date std_start_date, Date std_end_date) {
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


//        // 게시판 생성 및 연결
//        Study_group_post studyGroupPost = new Study_group_post();
//        studyGroupPost.setGroup_notice(notice);
//        studyGroupPost.setGroup_post_id(1L); // 첫 게시글 ID (규칙)
//        studyGroupPost.setGroup_post_content("규칙: " + notice);
//        studyGroupPost.setGroup_post_writer(leader.getLoginId());
//        studyGroupPost.setGroup_post_date(new Date());
//
//        studygrouppostRepository.save(studyGroupPost);

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

        // 게시판 규칙 조회
        Study_group_post studyGroupPost = studygrouppostRepository.findById(std_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 스터디의 게시판 규칙을 찾을 수 없습니다."));

        // 세부 정보 구성
        StringBuilder details = new StringBuilder();
        details.append("스터디 설명: ").append(studyGroup.getStd_description()).append("\n");
        details.append("스터디 규칙: ").append(studyGroupPost.getGroupNotice()).append("\n");
        details.append("현재 멤버 수: ").append(studyGroup.getStd_member_total()).append("\n");

        return details.toString();
    }
}
