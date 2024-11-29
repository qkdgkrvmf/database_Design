package com.database_Design.Database_Design.Repository;

import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.Study_group_post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudygrouppostRepository extends JpaRepository<Study_group_post,Long> {

    // 특정 스터디 그룹의 공지사항 조회
    // JPQL 기반 쿼리
    @Query("SELECT s.group_rule FROM Study_group s WHERE s = :studyGroup")
    Optional<String> findGroupRule(@Param("studyGroup") Study_group studyGroup);

}
