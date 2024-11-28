package com.database_Design.Database_Design.Repository;

import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.Study_group_member;
import com.database_Design.Database_Design.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudygroupMemberRepository extends JpaRepository<Study_group_member, Long> {

    // 특정 스터디 그룹과 사용자로 멤버 삭제
    void deleteByStudyGroupAndUser(Study_group studyGroup, User user);

    // 특정 스터디 그룹의 모든 멤버 조회
    List<Study_group_member> findByStudyGroup(Study_group studyGroup);
}
