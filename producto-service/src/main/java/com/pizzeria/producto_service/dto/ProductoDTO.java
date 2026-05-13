package com.pizzeria.producto_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Long categoriaId;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Boolean disponible;

}
