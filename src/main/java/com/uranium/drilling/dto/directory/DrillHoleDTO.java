package com.uranium.drilling.dto.directory;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class DrillHoleDTO {
    @NotBlank(message = "Системный код Скважины обязателен и не может быть пустым")
    @Size(min = 1, max = 255, message = "Имя должно содержать от 3 до 255 символов")
    String systemId;
    @NotBlank(message = "Имя Скважины обязательно и не может быть пустым")
    @Size(min = 3, max = 255, message = "Имя должно содержать от 3 до 255 символов")
    String name;
    @NotNull(message = "Статус активности обязателен к заполнению")
    Boolean isActive;
    @NotNull(message = "Участок обязателен к заполению")
    Long areaId;
    @NotNull(message = "Вид скважины обязателен к заполению")
    Long typeId;
    @NotNull(message = "Дата выдачи задания обязательна")
    Date taskIssueDate;
    @NotNull(message = "Дата начала бурения обязательна")
    Date startDate;
    @NotNull(message = "Глубина обязательна")
    @DecimalMin(value = "0.0", inclusive = false, message = "Глубина должна быть положительным числом")
    Double depth;
}
