package com.pizzeria.pago_service.dto;

import java.math.BigDecimal;

import com.pizzeria.pago_service.model.EstadoPago;
import com.pizzeria.pago_service.model.MetodoPago;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Long pedidoId;
    private BigDecimal monto;
    private MetodoPago metodo;
    private EstadoPago estado;

}
