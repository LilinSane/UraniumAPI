package com.uranium.drilling.repository.directory;

import com.uranium.drilling.entity.directory.DrillingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrillingTypeRepository extends JpaRepository<DrillingType, Long> {
}
