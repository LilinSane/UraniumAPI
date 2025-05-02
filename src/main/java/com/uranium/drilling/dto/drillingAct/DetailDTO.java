package com.uranium.drilling.dto.drillingAct;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class DetailDTO {

    @NotNull(message = "Смена обязательна к заполнению")
    private Integer shift;

    @NotNull(message = "Вахта обязательна к заполнению")
    private Integer rotation;

    @NotNull(message = "Дата обязательна к заполнению")
    private Date date;

    @NotNull(message = "Время начала обязательно к заполнению")
    private LocalTime startTime;

    @NotNull(message = "Время окончания обязательно к заполнению")
    private LocalTime endTime;

    @NotNull(message = "Глубина обязательна к заполнению")
    @DecimalMin(value = "0.0", inclusive = false, message = "Глубина должна быть положительным числом")
    private Double depth;

    @NotNull(message = "Заголовок обязателен к заполнению")
    private Long headerId;

    @NotNull(message = "Агрегат обязателен к заполнению")
    private Long drillingUnitId;

    @NotNull(message = "Подвид работ обязателен к заполнению")
    private Long workSubTypeId;

    private String resultGIS;
    private String drillHoleState;
    private Double acted;
}
