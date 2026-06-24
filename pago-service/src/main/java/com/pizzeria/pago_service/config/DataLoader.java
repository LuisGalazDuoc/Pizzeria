package com.pizzeria.pago_service.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pizzeria.pago_service.model.EstadoPago;
import com.pizzeria.pago_service.model.MetodoPago;
import com.pizzeria.pago_service.model.Pago;
import com.pizzeria.pago_service.repository.PagoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final PagoRepository pagoRepository;

    @Override
    public void run(String... args) {
        if (pagoRepository.count() >= 13) {
            return;
        }

        Pago p1 = new Pago();
        p1.setPagoId(8L);
        p1.setMonto(new BigDecimal("8490.00"));
        p1.setMetodo(MetodoPago.Efectivo);
        p1.setEstado(EstadoPago.Pendiente);
        pagoRepository.save(p1);

        Pago p2 = new Pago();
        p2.setPagoId(9L);
        p2.setMonto(new BigDecimal("17980.00"));
        p2.setMetodo(MetodoPago.Credito);
        p2.setEstado(EstadoPago.Completado);
        p2.setFechaPago(LocalDateTime.now());
        pagoRepository.save(p2);

        Pago p3 = new Pago();
        p3.setPagoId(10L);
        p3.setMonto(new BigDecimal("990.00"));
        p3.setMetodo(MetodoPago.Transferencia);
        p3.setEstado(EstadoPago.Pendiente);
        pagoRepository.save(p3);
    }

}
