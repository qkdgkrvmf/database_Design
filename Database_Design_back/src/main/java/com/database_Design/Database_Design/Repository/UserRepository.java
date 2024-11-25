package com.database_Design.Database_Design.Repository;

import com.database_Design.Database_Design.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // userId를 기준으로 사용자 조회
    Optional<User> findByUserid(String userId);
}
