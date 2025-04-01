package com.uranium.drilling.repository.directory;

import com.uranium.drilling.entity.directory.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByIsActiveTrue();

    boolean existsByTaxpayerNum(String taxpayerNum);

    @Query("""
        SELECT COUNT(a) > 0 FROM Area a WHERE a.customer.id = :customerId
    """)
    boolean hasLinkedAreas(@Param("customerId") Long customerId);
}
