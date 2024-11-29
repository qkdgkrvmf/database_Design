package com.database_Design.Database_Design.Repository;

import com.database_Design.Database_Design.entity.Study_group;
import com.database_Design.Database_Design.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudygroupRepository extends JpaRepository<Study_group,Long> {

    // 모집 중인 스터디 조회
    List<Study_group> findByStdstate(boolean stdstate);

}
