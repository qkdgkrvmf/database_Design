package com.database_Design.Database_Design.service;

import com.database_Design.Database_Design.Repository.StudygroupRepository;
import com.database_Design.Database_Design.Repository.UserRepository;
import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class StudygroupService {
    private final StudygroupRepository studyGroupRepository;
    private final UserRepository userRepository;

    // 그룹 가입
    public Study_group joinGroup(Long stdId, String userId) {
        Study_group studyGroup = studyGroupRepository.findById(stdId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

        if (studyGroup.getStd_member_total() >= 10) { // 최대 멤버 제한
            throw new IllegalStateException("스터디 그룹 정원이 가득 찼습니다.");
        }

        User user = userRepository.findByUserid(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        studyGroup.setStd_member_total(studyGroup.getStd_member_total() + 1);

        return studyGroupRepository.save(studyGroup);
    }

    // 그룹 탈퇴
    public Study_group leaveGroup(Long stdId, String userId) {
        Study_group studyGroup = studyGroupRepository.findById(stdId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

        if (studyGroup.getStd_member_total() <= 0) {
            throw new IllegalStateException("스터디 그룹에 멤버가 없습니다.");
        }

        User user = userRepository.findByUserid(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        studyGroup.setStd_member_total(studyGroup.getStd_member_total() - 1);

        return studyGroupRepository.save(studyGroup);
    }

    // 그룹 종료
    public Study_group closeGroup(Long stdId) {
        Study_group studyGroup = studyGroupRepository.findById(stdId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디 그룹입니다."));

        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        studyGroup.setStd_end_date(date);

        return studyGroupRepository.save(studyGroup);
    }
}
