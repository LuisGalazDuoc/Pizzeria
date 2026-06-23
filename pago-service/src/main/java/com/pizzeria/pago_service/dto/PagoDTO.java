package com.pizzeria.pago_service.dto;

import java.math.BigDecimal;

import com.pizzeria.pago_service.model.EstadoPago;
import com.pizzeria.pago_service.model.MetodoPago;

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
public class PagoDTO {

    @NotNull(message = "El ID de la orden es obligatorio")
    @Positive(message = "El ID de la orden debe ser positivo")
    private Long pedidoId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El monto debe tener máximo 2 decimales")
    private BigDecimal monto;

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodo;

    private EstadoPago estado;

}
