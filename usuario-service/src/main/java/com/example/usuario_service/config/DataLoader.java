package com.example.usuario_service.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.usuario_service.model.Usuario;
import com.example.usuario_service.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() >= 13) {
            return;
        }

        Usuario u1 = new Usuario();
        u1.setRut(22334455L);
        u1.setDvrut("5");
        u1.setNombre("Francisca Reyes");
        u1.setEmail("francisca.reyes@gmail.com");
        u1.setTelefono("+56922334455");
        usuarioRepository.save(u1);

        Usuario u2 = new Usuario();
        u2.setRut(33445566L);
        u2.setDvrut("6");
        u2.setNombre("Tomás Espinoza");
        u2.setEmail("tomas.espinoza@gmail.com");
        u2.setTelefono("+56933445566");
        usuarioRepository.save(u2);

        Usuario u3 = new Usuario();
        u3.setRut(44556677L);
        u3.setDvrut("7");
        u3.setNombre("Javiera Muñoz");
        u3.setEmail("javiera.munoz@gmail.com");
        u3.setTelefono("+56944556677");
        usuarioRepository.save(u3);
    }

}
