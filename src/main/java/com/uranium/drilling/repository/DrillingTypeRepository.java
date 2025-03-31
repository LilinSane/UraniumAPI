package com.uranium.drilling.repository;

import com.uranium.drilling.entity.DrillingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrillingTypeRepository extends JpaRepository<DrillingType, Long> {
}
