package com.uranium.drilling.entity.drillingAct;

import com.uranium.drilling.entity.directory.DrillingUnit;
import com.uranium.drilling.entity.directory.WorkSubType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    Integer shift;
    Integer rotation;
    Date date;
    LocalTime startTime;
    LocalTime endTime;
    Double depth;
    @ManyToOne
    @JoinColumn(name = "header_id")
    Header header;
    @ManyToOne
    @JoinColumn(name = "drilling_unit_id")
    DrillingUnit drillingUnit;
    @ManyToOne
    @JoinColumn(name = "work_sub_type_id")
    WorkSubType workSubType;
    String resultGIS;
    String drillHoleState;
}
