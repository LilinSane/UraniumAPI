package com.uranium.drilling.entity.drillingAct;

import com.uranium.drilling.entity.directory.DrillingUnit;
import com.uranium.drilling.entity.directory.WorkSubType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
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
    Double workedTime;
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
    Double acted;

    @PrePersist
    @PreUpdate
    public void calculateWorkedTime() {
        if (startTime != null && endTime != null) {
            long minutes = Duration.between(startTime, endTime).toMinutes();

            if (minutes < 0) {
                minutes += 24 * 60;
            }

            this.workedTime = Math.round((minutes / 60.0) * 100.0) / 100.0;
        } else {
            this.workedTime = null;
        }
    }
}

