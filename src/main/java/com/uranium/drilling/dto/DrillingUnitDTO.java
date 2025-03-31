package com.uranium.drilling.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DrillingUnitDTO {
    @NotBlank(message = "Имя Агрегата обязательно и не может быть пустым")
    @Size(min = 3, max = 255, message = "Имя должно содержать от 3 до 255 символов")
    String name;
    @NotBlank(message = "Инвентарный номер обязателен и не может быть пустым")
    @Size(min = 3, max = 255, message = "Имя должно содержать от 3 до 255 символов")
    String inventoryNumber;
    @NotNull(message = "Статус активности обязателен к заполнению")
    Boolean isActive;
}
