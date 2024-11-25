package com.database_Design.Database_Design.Repository;

import com.database_Design.Database_Design.entity.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer,Long> {
}
