package com.uranium.drilling.repository.directory;

import com.uranium.drilling.entity.directory.WorkDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkDirectionRepository extends JpaRepository<WorkDirection, Long> {

    @Query("""
        SELECT COUNT(w) > 0 FROM WorkType w WHERE w.workDirection.id = :workDirectionId
    """)
    boolean hasLinkedWorkTypes(@Param("workDirectionId") Long workDirectionId);
}
