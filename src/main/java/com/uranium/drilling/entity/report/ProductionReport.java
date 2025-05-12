package com.uranium.drilling.entity.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionReport {
    String drillHoleId;
    String drillingUnitName;
    String areaName;
    String customerName;
    String drillingTypeName;
    Integer drilledDrillHoles;
    Double drilledDepth;
    Integer actedDrillHoles;
    Double actedDepth;
    Double workedTime;
    Double downtime;
}
