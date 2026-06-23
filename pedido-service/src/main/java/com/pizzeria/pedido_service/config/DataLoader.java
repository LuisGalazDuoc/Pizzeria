package com.pizzeria.pedido_service.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pizzeria.pedido_service.model.Pedido;
import com.pizzeria.pedido_service.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) {
        if (pedidoRepository.count() >= 13) {
            return;
        }

        Pedido o1 = new Pedido();
        o1.setUsuarioId(11L);
        o1.setDireccionId(11L);
        o1.setMontoTotal(new BigDecimal("8990.00"));
        o1.setFechaCreacion(LocalDateTime.now());
        pedidoRepository.save(o1);

        Pedido o2 = new Pedido();
        o2.setUsuarioId(12L);
        o2.setDireccionId(12L);
        o2.setMontoTotal(new BigDecimal("12480.00"));
        o2.setFechaCreacion(LocalDateTime.now());
        pedidoRepository.save(o2);

        Pedido o3 = new Pedido();
        o3.setUsuarioId(13L);
        o3.setDireccionId(13L);
        o3.setMontoTotal(new BigDecimal("9990.00"));
        o3.setFechaCreacion(LocalDateTime.now());
        pedidoRepository.save(o3);
    }

}
