package com.pizzeria.pedido_service.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pizzeria.pedido_service.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    List<Pedido> findByUsuarioId(Long usuarioId);
    ///List<Pedido> findByStatus(PedidoEstado status); ---> Utilizar con el atributo enum PedidoEstado
    List<Pedido> findByFechaCreacionBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COALESCE(SUM(p.montoTotal), 0) FROM Pedido p")
    BigDecimal sumMontoTotalPedido();

}
