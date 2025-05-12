package com.uranium.drilling.repository.directory;

import com.uranium.drilling.entity.directory.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    List<Area> findByIsActiveTrue();

    @Query("""
        SELECT COUNT(d) > 0 FROM DrillHole d WHERE d.area.id = :areaId
    """)
    boolean hasLinkedDrillHole(@Param("areaId") Long areaId);
}


