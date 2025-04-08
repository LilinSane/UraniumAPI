package com.uranium.drilling.dto.drillingAct;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class HeaderDTO {
    @NotNull(message = "Дата акта обязательна")
    private Date date;

    @NotNull(message = "Статус активности обязателен к заполнению")
    private Boolean isActive;

    @NotNull(message = "Вид бурения обязателен к заполнению")
    private Long drillingTypeId;

    @NotNull(message = "Скважина обязательна к заполнению")
    private Long drillHoleId;
}
