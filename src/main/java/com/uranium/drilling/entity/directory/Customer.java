package com.uranium.drilling.entity.directory;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "customer_seq", sequenceName = "customer_id_seq", allocationSize = 1)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    Long id;
    String name;
    @Column(unique = true)
    String taxpayerNum;
    Boolean isActive;
}
