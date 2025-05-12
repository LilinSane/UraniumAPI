package com.uranium.drilling.repository.drillingAct;

import com.uranium.drilling.entity.drillingAct.Detail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {
    Page<Detail> findByHeaderId(Long headerId, Pageable pageable);

    List<Detail> findByDateBetween(Date from, Date to);
}
