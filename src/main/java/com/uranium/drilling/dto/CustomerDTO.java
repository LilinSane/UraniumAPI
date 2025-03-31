package com.uranium.drilling.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    @NotBlank(message = "Имя Заказчика обязательно и не может быть пустым")
    @Size(min = 3, max = 255, message = "Имя должно содержать от 3 до 255 символов")
    String name;

    @NotNull(message = "Статус активности обязателен к заполнению")
    Boolean isActive;
}
