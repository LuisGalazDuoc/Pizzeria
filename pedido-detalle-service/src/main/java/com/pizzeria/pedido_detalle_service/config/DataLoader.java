package com.pizzeria.pedido_detalle_service.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pizzeria.pedido_detalle_service.model.PedidoDetalle;
import com.pizzeria.pedido_detalle_service.repository.PedidoDetalleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final PedidoDetalleRepository detalleRepository;

    @Override
    public void run(String... args) {
        if (detalleRepository.count() >= 13) {
            return;
        }

        PedidoDetalle d1 = new PedidoDetalle();
        d1.setPedidoId(8L);
        d1.setProductoId(1L);
        d1.setCantidad(1);
        d1.setPrecioUnidad(new BigDecimal("8490.00"));
        d1.setSubtotal(new BigDecimal("8490.00"));
        detalleRepository.save(d1);

        PedidoDetalle d2 = new PedidoDetalle();
        d2.setPedidoId(9L);
        d2.setProductoId(5L);
        d2.setCantidad(2);
        d2.setPrecioUnidad(new BigDecimal("8990.00"));
        d2.setSubtotal(new BigDecimal("17980.00"));
        detalleRepository.save(d2);

        PedidoDetalle d3 = new PedidoDetalle();
        d3.setPedidoId(10L);
        d3.setProductoId(7L);
        d3.setCantidad(1);
        d3.setPrecioUnidad(new BigDecimal("990.00"));
        d3.setSubtotal(new BigDecimal("990.00"));
        detalleRepository.save(d3);
    }

}
