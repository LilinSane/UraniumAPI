package com.uranium.drilling.repository.drillingAct;

import com.uranium.drilling.entity.drillingAct.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {
}
