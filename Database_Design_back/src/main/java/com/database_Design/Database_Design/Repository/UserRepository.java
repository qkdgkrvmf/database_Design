package com.database_Design.Database_Design.Repository;

import com.database_Design.Database_Design.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
