package com.uranium.drilling.repository.drillingAct;

import com.uranium.drilling.entity.directory.DrillHole;
import com.uranium.drilling.entity.drillingAct.Header;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeaderRepository extends JpaRepository<Header, Long> {
    List<Header> findByIsActiveTrue();

}
