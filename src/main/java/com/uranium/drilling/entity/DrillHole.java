package com.uranium.drilling.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrillHole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String systemId;
    String name;
    Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "area_id")
    Area area;
    @ManyToOne
    @JoinColumn(name = "type_id")
    DrillHoleType drillHoleType;
}
