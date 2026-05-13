package com.pizzeria.pago_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Long pedidoId;
    private BigDecimal monto;
    /*
    private MetodoPago method;
    private EstadoPago status;
    */

}
