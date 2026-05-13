package com.pizzeria.pago_service.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pizzeria.pago_service.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    Optional<Pago> findByPedidoId(Long pedidoId);
    boolean existsByPedidoId(Long orderId);
    ///List<Pago> findByMethod(MetodoPago method);  --->    Trabajar con atributo enum

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Pago p")
    BigDecimal sumTotalPago();

}
