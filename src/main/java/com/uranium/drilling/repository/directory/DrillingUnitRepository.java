package com.uranium.drilling.repository.directory;

import com.uranium.drilling.entity.directory.DrillingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrillingUnitRepository extends JpaRepository<DrillingUnit, Long> {
}
