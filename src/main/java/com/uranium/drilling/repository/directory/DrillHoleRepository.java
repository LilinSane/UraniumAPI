package com.uranium.drilling.repository.directory;

import com.uranium.drilling.entity.directory.DrillHole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrillHoleRepository extends JpaRepository<DrillHole, Long> {
    List<DrillHole> findByIsActiveTrue();

    @Query("""
        SELECT COUNT(h) > 0 FROM Header h WHERE h.drillHole.id = :drillHoleId
    """)
    boolean hasLinkedHeader(@Param("drillHoleId") Long drillHoleId);
}
