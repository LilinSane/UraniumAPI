package com.uranium.drilling.repository;

import com.uranium.drilling.entity.WorkSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSubTypeRepository extends JpaRepository<WorkSubType, Long> {
}
