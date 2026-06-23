package com.pizzeria.envio_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pizzeria.envio_service.model.Envio;
import com.pizzeria.envio_service.repository.EnvioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final EnvioRepository envioRepository;

    @Override
    public void run(String... args) {
        if (envioRepository.count() >= 13) {
            return;
        }

        Envio d1 = new Envio();
        d1.setPedidoId(2L);
        envioRepository.save(d1);

        Envio d2 = new Envio();
        d2.setPedidoId(10L);
        envioRepository.save(d2);
    }

}
