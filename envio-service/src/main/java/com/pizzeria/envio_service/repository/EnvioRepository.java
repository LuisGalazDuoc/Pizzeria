package com.pizzeria.envio_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizzeria.envio_service.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

    Optional<Envio> findByPedidoId(Long pedidoId);
    boolean existsByPedidoId(Long pedidoId);
    ///List<Envio> findByDriverName(String driverName);  --->  Trabajar para evaluacion 3
    List<Envio> findByFechaEnvioBetween(LocalDateTime start, LocalDateTime end);

}
