package com.pizzeria.pedido_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long usuarioId;
    private Long direccionId;
    private BigDecimal montoTotal;
    ///private PedidoEstado estado;     --->    Trabajarlo con atributo enum 

}
