package com.uranium.drilling.repository;

import com.uranium.drilling.entity.Customer;
import com.uranium.drilling.entity.DrillHole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrillHoleRepository extends JpaRepository<DrillHole, Long> {
    List<DrillHole> findByIsActiveTrue();

//    @Query("SELECT COUNT(d) > 0 FROM DrillHole d WHERE d.area.id = :id OR d.drillHoleType.id = :id")
//    boolean hasLinkedEntities(Long id);
}
