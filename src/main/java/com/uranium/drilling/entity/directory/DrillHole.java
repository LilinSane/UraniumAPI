package com.uranium.drilling.entity.directory;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    Date taskIssueDate;
    Date startDate;
    Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "area_id")
    Area area;
    @ManyToOne
    @JoinColumn(name = "type_id")
    DrillHoleType drillHoleType;
    Double depth;
}
