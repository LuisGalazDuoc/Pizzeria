package com.pizzeria.categoria_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pizzeria.categoria_service.model.Categoria;
import com.pizzeria.categoria_service.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() >= 13) {
            return;
        }

        Categoria c1 = new Categoria();
        c1.setNombre("Salsas Picantes");
        categoriaRepository.save(c1);

        Categoria c2 = new Categoria();
        c2.setNombre("Salsas Dulces");
        categoriaRepository.save(c2);

        Categoria c3 = new Categoria();
        c3.setNombre("Salsas Acidas");
        categoriaRepository.save(c3);
    }

}
