package com.uranium.drilling.repository.directory;

import com.uranium.drilling.entity.directory.DrillingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DrillingTypeRepository extends JpaRepository<DrillingType, Long> {

    @Query("""
        SELECT COUNT(h) > 0 FROM Header h WHERE h.drillingType.id = :drillingTypeId
    """)
    boolean hasLinkedHeader(@Param("drillingTypeId") Long drillingTypeId);
}
