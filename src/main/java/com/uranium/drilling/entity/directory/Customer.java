package com.uranium.drilling.entity.directory;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String name;
    @Column(unique = true)
    String taxpayerNum;
    Boolean isActive;
}
