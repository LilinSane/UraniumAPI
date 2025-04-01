package com.uranium.drilling.repository.directory;

import com.uranium.drilling.entity.directory.WorkSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSubTypeRepository extends JpaRepository<WorkSubType, Long> {
}
