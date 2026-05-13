package com.pizzeria.direccion_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {

    private Long usuarioId;
    private String calle;
    private String comuna;
    private Boolean esDefault;

}
