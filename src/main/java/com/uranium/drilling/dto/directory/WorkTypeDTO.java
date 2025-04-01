package com.uranium.drilling.dto.directory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WorkTypeDTO {
    @NotBlank(message = "Имя Вида работы обязательно и не может быть пустым")
    @Size(min = 3, max = 255, message = "Имя должно содержать от 3 до 255 символов")
    String name;
    @NotNull(message = "Направление обязательно к заполению")
    Long directionId;
}
