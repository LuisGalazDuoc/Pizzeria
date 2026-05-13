package com.pizzeria.direccion_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizzeria.direccion_service.model.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    List<Direccion> findByUsuarioId(Long usuarioId);
    Optional<Direccion> findByUsuarioIdAndEsDefaultTrue(Long usuarioId);
    boolean existsByUsuarioId(Long usuarioId);

}
