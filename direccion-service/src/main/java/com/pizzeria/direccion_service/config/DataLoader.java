package com.pizzeria.direccion_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pizzeria.direccion_service.model.Direccion;
import com.pizzeria.direccion_service.repository.DireccionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final DireccionRepository direccionRepository;

    @Override
    public void run(String... args) {
        if (direccionRepository.count() >= 13) {
            return;
        }

        Direccion d1 = new Direccion();
        d1.setUsuarioId(11L);
        d1.setCalle("Calle Nueva Esperanza 111");
        d1.setComuna("Independencia");
        d1.setEsDefault(true);
        direccionRepository.save(d1);

        Direccion d2 = new Direccion();
        d2.setUsuarioId(12L);
        d2.setCalle("Av. Lo Espejo 222");
        d2.setComuna("San Miguel");
        d2.setEsDefault(true);
        direccionRepository.save(d2);

        Direccion d3 = new Direccion();
        d3.setUsuarioId(13L);
        d3.setCalle("Pasaje Las Camelias 333");
        d3.setComuna("Quilicura");
        d3.setEsDefault(true);
        direccionRepository.save(d3);
    }

}
