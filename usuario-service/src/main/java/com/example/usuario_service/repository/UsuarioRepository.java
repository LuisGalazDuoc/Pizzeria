package com.example.usuario_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.usuario_service.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByRut(Long rut); ///Buscar forma para usar el dvrut+rut
    boolean existsByRut(Long rut);
    boolean existsByEmail(String email);

}
