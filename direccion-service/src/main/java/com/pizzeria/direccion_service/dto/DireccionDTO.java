package com.pizzeria.direccion_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser un número positivo")
    private Long usuarioId;

    @NotBlank(message = "La calle es obligatoria")
    @Size(min = 5, max = 150, message = "La calle debe tener entre 5 y 150 caracteres")
    private String calle;

    @NotBlank(message = "La comuna es obligatoria")
    @Size(min = 3, max = 100, message = "La comuna debe tener entre 3 y 100 caracteres")
    private String comuna;

    @NotNull(message = "Debe indicar si es la dirección por defecto")
    private Boolean esDefault;

}
