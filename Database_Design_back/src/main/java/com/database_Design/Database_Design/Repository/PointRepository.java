package com.database_Design.Database_Design.Repository;

import com.database_Design.Database_Design.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point,Long> {
}
