package com.pizzeria.pedido_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser positivo")
    private Long usuarioId;

    @NotNull(message = "El ID de la dirección es obligatorio")
    @Positive(message = "El ID de la dirección debe ser positivo")
    private Long direccionId;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El total debe tener máximo 2 decimales")
    private BigDecimal montoTotal;
    ///private PedidoEstado estado;     --->    Trabajarlo con atributo enum 

}
