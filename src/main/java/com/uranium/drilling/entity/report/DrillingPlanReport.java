package com.uranium.drilling.entity.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrillingPlanReport {
    String drillHoleId;
    String drillingUnitName;
    String areaName;
    String customerName;
    String drillingTypeName;
    Double planedDepth;
    Double drilledDepth;
    Double actedDepth;
    Double workedTime;
}
