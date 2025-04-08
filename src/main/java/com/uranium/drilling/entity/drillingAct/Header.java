package com.uranium.drilling.entity.drillingAct;

import com.uranium.drilling.entity.directory.DrillHole;
import com.uranium.drilling.entity.directory.DrillingType;
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
public class Header {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    Date date;
    Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "type_id")
    DrillingType drillingType;
    @ManyToOne
    @JoinColumn(name = "drill_hole_id")
    DrillHole drillHole;
}
