package com.uranium.drilling.repository;

import com.uranium.drilling.entity.DrillingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrillingUnitRepository extends JpaRepository<DrillingUnit, Long> {
}
