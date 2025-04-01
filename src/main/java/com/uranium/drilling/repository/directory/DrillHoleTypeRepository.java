package com.uranium.drilling.repository.directory;

import com.uranium.drilling.entity.directory.DrillHoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DrillHoleTypeRepository extends JpaRepository<DrillHoleType, Long> {

    @Query("""
        SELECT COUNT(d) > 0 FROM DrillHole d WHERE d.drillHoleType.id = :drillHoleTypeId
    """)
    boolean hasLinkedDrillHoles(@Param("drillHoleTypeId") Long drillHoleTypeId);
}
