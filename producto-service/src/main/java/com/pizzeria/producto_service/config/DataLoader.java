package com.pizzeria.producto_service.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pizzeria.producto_service.model.Producto;
import com.pizzeria.producto_service.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) {
        if (productoRepository.count() >= 13) {
            return;
        }

        Producto p1 = new Producto();
        p1.setCategoriaId(1L);
        p1.setNombre("Pizza BBQ Bacon");
        p1.setDescripcion("Salsa BBQ, bacon grillado y cebolla morada");
        p1.setPrecio(new BigDecimal("9990.00"));
        p1.setDisponible(false);
        productoRepository.save(p1);

        Producto p2 = new Producto();
        p2.setCategoriaId(2L);
        p2.setNombre("Limonada 500ml");
        p2.setDescripcion("Limonada natural con menta");
        p2.setPrecio(new BigDecimal("9990.00"));
        p2.setDisponible(false);
        productoRepository.save(p2);

        Producto p3 = new Producto();
        p3.setCategoriaId(3L);
        p3.setNombre("Pan de Ajo");
        p3.setDescripcion("Pan artesanal con mantequilla de ajo y hierbas");
        p3.setPrecio(new BigDecimal("3490.00"));
        p3.setDisponible(true);
        productoRepository.save(p3);
    }

}
