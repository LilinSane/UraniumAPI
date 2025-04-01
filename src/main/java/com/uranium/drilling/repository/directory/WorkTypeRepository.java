package com.uranium.drilling.repository.directory;

import com.uranium.drilling.entity.directory.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Long> {

    @Query("""
        SELECT COUNT(w) > 0 FROM WorkSubType w WHERE w.workType.id = :workTypeId
    """)
    boolean hasLinkedWorkSubTypes(@Param("workTypeId") Long workTypeId);
}
