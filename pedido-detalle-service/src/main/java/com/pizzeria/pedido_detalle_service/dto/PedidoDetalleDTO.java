package com.pizzeria.pedido_detalle_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalleDTO {

    private Long pedidoId;
    private Long productoId;
    private Integer cantidad;
    private BigDecimal precioUnidad;

}
